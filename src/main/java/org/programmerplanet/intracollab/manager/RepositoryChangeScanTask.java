package org.programmerplanet.intracollab.manager;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.programmerplanet.intracollab.model.RepositoryChange;
import org.programmerplanet.intracollab.model.SourceRepository;
import org.programmerplanet.intracollab.model.Ticket;

/**
 * <code>TimerTask</code> responsible for scanning source repositories for
 * changes and persisting those changes.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class RepositoryChangeScanTask extends TimerTask {

	private static final Log log = LogFactory.getLog(RepositoryChangeScanTask.class);

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		Collection<SourceRepository> sourceRepositories = projectManager.getSourceRepositories();
		for (SourceRepository sourceRepository : sourceRepositories) {
			try {
				processSourceRepository(sourceRepository);
			} catch (Exception e) {
				log.error("Error scanning repository: " + sourceRepository.getPath() + " [" + sourceRepository.getModules() + "]", e);
			}
		}
	}

	private void processSourceRepository(SourceRepository sourceRepository) {
		log.debug("Scanning for repository changes: " + sourceRepository.getPath() + " [" + sourceRepository.getModules() + "]");
		List<RepositoryChange> changes = sourceRepository.getRepositoryChanges();
		log.debug("Found " + changes.size() + " change(s).");
		if (!changes.isEmpty()) {
			findAndAttachTicketReferences(changes);
			Date lastChangeDate = getLastChangeDate(changes);
			sourceRepository.setLastChangeDate(lastChangeDate);
			projectManager.saveRepositoryChanges(sourceRepository, changes);
		}
	}

	private Date getLastChangeDate(Collection<RepositoryChange> changes) {
		Date lastChangeDate = null;
		for (RepositoryChange change : changes) {
			Date changeDate = change.getChangeDate();
			if (lastChangeDate == null || changeDate.after(lastChangeDate)) {
				lastChangeDate = changeDate;
			}
		}
		return lastChangeDate;
	}

	private void findAndAttachTicketReferences(Collection<RepositoryChange> changes) {
		for (RepositoryChange change : changes) {
			Collection<Ticket> tickets = findReferencedTickets(change);
			change.setTickets(new HashSet<Ticket>(tickets));
		}
	}

	private Collection<Ticket> findReferencedTickets(RepositoryChange change) {
		Collection<Ticket> tickets = new LinkedList<Ticket>();
		Collection<Long> ticketNumbers = change.findTicketNumbersInComment();
		for (Long id : ticketNumbers) {
			Ticket ticket = projectManager.getTicket(id);
			if (ticket != null) {
				tickets.add(ticket);
			}
		}
		return tickets;
	}

}
