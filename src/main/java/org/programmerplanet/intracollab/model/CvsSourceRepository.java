package org.programmerplanet.intracollab.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.netbeans.lib.cvsclient.Client;
import org.netbeans.lib.cvsclient.admin.StandardAdminHandler;
import org.netbeans.lib.cvsclient.command.CommandException;
import org.netbeans.lib.cvsclient.command.GlobalOptions;
import org.netbeans.lib.cvsclient.command.log.LogInformation;
import org.netbeans.lib.cvsclient.command.log.RlogCommand;
import org.netbeans.lib.cvsclient.command.log.LogInformation.Revision;
import org.netbeans.lib.cvsclient.connection.AuthenticationException;
import org.netbeans.lib.cvsclient.connection.LocalConnection;
import org.netbeans.lib.cvsclient.event.CVSAdapter;
import org.netbeans.lib.cvsclient.event.FileInfoEvent;
import org.springframework.beans.support.PropertyComparator;

/**
 * Represents a CVS source repository for the project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class CvsSourceRepository extends SourceRepository {

	public List<RepositoryChange> getRepositoryChanges() {
		Collection<LogInformation> logInformationCollection;
		try {
			logInformationCollection = getLogInformationCollection();
		} catch (AuthenticationException e) {
			throw new SourceRepositoryAccessException(e);
		} catch (CommandException e) {
			throw new SourceRepositoryAccessException(e);
		} catch (IOException e) {
			throw new SourceRepositoryAccessException(e);
		}
		List<RepositoryChange> changes = createRepositoryChanges(logInformationCollection);
		return changes;
	}

	private List<RepositoryChange> createRepositoryChanges(Collection<LogInformation> logInformationCollection) {
		List<RepositoryChange> changes = new LinkedList<RepositoryChange>();

		for (LogInformation logInformation : logInformationCollection) {
			List revisionList = logInformation.getRevisionList();
			for (Iterator i = revisionList.iterator(); i.hasNext();) {
				Revision revision = (Revision)i.next();
				RepositoryChange change = findRepositoryChange(changes, revision.getDate(), revision.getAuthor(), revision.getMessage());
				if (change == null) {
					change = createRepositoryChange(revision);
					changes.add(change);
				}
				RepositoryChangeFile file = createRepositoryChangeFile(logInformation, revision);
				file.setRepositoryChange(change);
				change.getFiles().add(file);
			}
		}

		Collections.sort(changes, new PropertyComparator("changeDate", false, true));

		return changes;
	}

	private RepositoryChangeFile createRepositoryChangeFile(LogInformation logInformation, Revision revision) {
		RepositoryChangeFile file = new RepositoryChangeFile();
		String repositoryFilename = logInformation.getRepositoryFilename();
		String filename = convertRepositoryFilename(repositoryFilename);
		file.setFilename(filename);
		file.setRevision(revision.getNumber());
		return file;
	}

	private String convertRepositoryFilename(String repositoryFilename) {
		String filename = repositoryFilename;
		if (filename.startsWith(getPath())) {
			filename = filename.substring(getPath().length());
		}
		if (filename.endsWith(",v")) {
			filename = filename.substring(0, filename.length() - 2);
		}
		if (!filename.startsWith("/")) {
			filename = "/" + filename;
		}
		return filename;
	}

	private RepositoryChange createRepositoryChange(Revision revision) {
		RepositoryChange change = new RepositoryChange();
		change.setProject(getProject());
		change.setChangeDate(revision.getDate());
		change.setUsername(revision.getAuthor());
		change.setComment(revision.getMessage());
		change.setFiles(new HashSet<RepositoryChangeFile>());
		return change;
	}

	private RepositoryChange findRepositoryChange(Collection<RepositoryChange> changes, Date changeDate, String username, String comment) {
		Predicate datePredicate = new BeanPropertyValueEqualsPredicate("changeDate", changeDate);
		Predicate usernamePredicate = new BeanPropertyValueEqualsPredicate("username", username);
		Predicate commentPredicate = new BeanPropertyValueEqualsPredicate("comment", comment);
		Predicate predicate = PredicateUtils.allPredicate(new Predicate[] { datePredicate, usernamePredicate, commentPredicate });
		RepositoryChange change = (RepositoryChange)CollectionUtils.find(changes, predicate);
		return change;
	}

	private Collection<LogInformation> getLogInformationCollection() throws AuthenticationException, CommandException, IOException {
		GlobalOptions globalOptions = new GlobalOptions();
		globalOptions.setCVSRoot(getPath());

		LocalConnection connection = new LocalConnection();
		connection.setRepository(getPath());
		connection.open();

		LogInformationCaptureListener logInformationCaptureListener = new LogInformationCaptureListener();

		try {
			Client client = new Client(connection, new StandardAdminHandler());
			client.getEventManager().addCVSListener(logInformationCaptureListener);

			RlogCommand rlog = new RlogCommand();
			rlog.setRecursive(true);
			rlog.setHeaderOnly(false);
			rlog.setNoTags(true);

			String[] moduleNames = getModules().split(",");
			for (int i = 0; i < moduleNames.length; i++) {
				moduleNames[i] = moduleNames[i].trim();
			}
			rlog.setModules(moduleNames);

			if (getLastChangeDate() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String dateFilter = dateFormat.format(getLastChangeDate());
				rlog.setDateFilter(">" + dateFilter);
			}

			client.executeCommand(rlog, globalOptions);
		} finally {
			connection.close();
		}

		return logInformationCaptureListener.getLogInformationCollection();
	}

	private static final class LogInformationCaptureListener extends CVSAdapter {

		private Collection<LogInformation> logInformationCollection = new LinkedList<LogInformation>();

		private Collection<LogInformation> getLogInformationCollection() {
			return logInformationCollection;
		}

		public void fileInfoGenerated(FileInfoEvent event) {
			LogInformation logInformation = (LogInformation)event.getInfoContainer();
			logInformationCollection.add(logInformation);
		}

	}

}
