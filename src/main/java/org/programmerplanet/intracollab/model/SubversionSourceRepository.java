package org.programmerplanet.intracollab.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.support.PropertyComparator;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

/**
 * Represents a Subversion source repository for the project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class SubversionSourceRepository extends SourceRepository {

	/**
	 * @see org.programmerplanet.intracollab.model.SourceRepository#getRepositoryChanges()
	 */
	public List<RepositoryChange> getRepositoryChanges() {
		Collection<SVNLogEntry> svnLogEntries;
		try {
			svnLogEntries = getSvnLogEntries();
		} catch (SVNException e) {
			throw new SourceRepositoryAccessException(e);
		}
		List<RepositoryChange> changes = createRepositoryChanges(svnLogEntries);
		return changes;
	}

	/**
	 * The last change point for a Subversion repository is the latest revision.
	 * 
	 * @see org.programmerplanet.intracollab.model.SourceRepository#getLastChangePoint(java.util.List)
	 */
	public Long getLastChangePoint(List<RepositoryChange> repositoryChanges) {
		long lastRevision = -1;
		for (RepositoryChange change : repositoryChanges) {
			Set<RepositoryChangeFile> files = change.getFiles();
			for (RepositoryChangeFile file : files) {
				String revisionString = file.getRevision();
				long revision = Long.parseLong(revisionString);
				if (revision > lastRevision) {
					lastRevision = revision;
				}
			}
		}
		return Long.valueOf(lastRevision);
	}

	private List<RepositoryChange> createRepositoryChanges(Collection<SVNLogEntry> svnLogEntries) {
		List<RepositoryChange> changes = new LinkedList<RepositoryChange>();

		for (SVNLogEntry svnLogEntry : svnLogEntries) {
			RepositoryChange change = createRepositoryChange(svnLogEntry);
			changes.add(change);

			Map<Object, SVNLogEntryPath> changedPaths = svnLogEntry.getChangedPaths();
			Collection<SVNLogEntryPath> svnLogEntryPaths = changedPaths.values();
			for (SVNLogEntryPath svnLogEntryPath : svnLogEntryPaths) {
				RepositoryChangeFile file = createRepositoryChangeFile(svnLogEntryPath, svnLogEntry);
				file.setRepositoryChange(change);
				change.getFiles().add(file);

			}
		}

		Collections.sort(changes, new PropertyComparator("changeDate", false, true));
		return changes;
	}

	private RepositoryChange createRepositoryChange(SVNLogEntry svnLogEntry) {
		RepositoryChange change = new RepositoryChange();
		change.setProject(getProject());
		change.setChangeDate(svnLogEntry.getDate());
		change.setUsername(svnLogEntry.getAuthor());
		change.setComment(svnLogEntry.getMessage());
		change.setFiles(new HashSet<RepositoryChangeFile>());
		return change;
	}

	private RepositoryChangeFile createRepositoryChangeFile(SVNLogEntryPath svnLogEntryPath, SVNLogEntry svnLogEntry) {
		RepositoryChangeFile file = new RepositoryChangeFile();
		file.setFilename(svnLogEntryPath.getPath());
		file.setRevision(Long.toString(svnLogEntry.getRevision()));
		return file;
	}

	private Collection<SVNLogEntry> getSvnLogEntries() throws SVNException {
		FSRepositoryFactory.setup();

		SVNURL svnUrl = SVNURL.parseURIEncoded("file://" + this.getPath());
		SVNRepository svnRepository = SVNRepositoryFactory.create(svnUrl);

		String[] moduleNames = getModules().split(",");
		for (int i = 0; i < moduleNames.length; i++) {
			moduleNames[i] = moduleNames[i].trim();
		}

		long startRevision = -1;
		long endRevision = -1; //HEAD (the latest) revision
		if (this.getLastChangePoint() != null) {
			startRevision = this.getLastChangePoint().longValue() + 1;
		}

		Collection<SVNLogEntry> svnLogEntries = Collections.EMPTY_LIST;

		long latestRevision = svnRepository.getLatestRevision();
		if (latestRevision >= startRevision) {
			svnLogEntries = svnRepository.log(moduleNames, null, startRevision, endRevision, true, true);
		}

		return svnLogEntries;
	}

}
