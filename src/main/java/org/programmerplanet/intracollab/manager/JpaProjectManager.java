package org.programmerplanet.intracollab.manager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.programmerplanet.intracollab.model.Attachment;
import org.programmerplanet.intracollab.model.AttachmentInfo;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Component;
import org.programmerplanet.intracollab.model.Milestone;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.RepositoryChange;
import org.programmerplanet.intracollab.model.SourceRepository;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.TicketChange;
import org.programmerplanet.intracollab.model.User;
import org.programmerplanet.intracollab.model.search.AttachmentSearchResult;
import org.programmerplanet.intracollab.model.search.CommentSearchResult;
import org.programmerplanet.intracollab.model.search.RepositoryChangeSearchResult;
import org.programmerplanet.intracollab.model.search.SearchResult;
import org.programmerplanet.intracollab.model.search.TicketSearchResult;
import org.programmerplanet.intracollab.notification.NotificationManager;
import org.programmerplanet.intracollab.util.DateRange;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * JPA implementation of the <code>ProjectManager</code> interface.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class JpaProjectManager extends JpaDaoSupport implements ProjectManager {

	private NotificationManager notificationManager;

	public void setNotificationManager(NotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getProjects()
	 */
	public Collection<Project> getProjects() {
		return this.getJpaTemplate().find("SELECT e FROM Project AS e");
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getProject(java.lang.Long)
	 */
	public Project getProject(Long id) {
		return this.getJpaTemplate().find(Project.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getProject(java.lang.Long, java.lang.String[])
	 */
	public Project getProject(Long id, String... fetches) {
		return getWithFetches(Project.class, id, fetches);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveProject(org.programmerplanet.intracollab.model.Project)
	 */
	public void saveProject(Project project) {
		// there may be an orphan source repository that needs to be deleted manually
		// this is a workaround since orphans are not removed in JPA 1.0 one-to-one relationships 
		// this may not be necessary with JPA 2.0's orphanRemoval property on one-to-one relationships 
		SourceRepository orphanSourceRepository = null;
		if (project.getId() != null) {
			Project oldProject = this.getProject(project.getId(), "sourceRepository");
			if (!ObjectUtils.equals(oldProject.getSourceRepository(), project.getSourceRepository())) {
				orphanSourceRepository = oldProject.getSourceRepository();
			}
		}
		this.getJpaTemplate().merge(project);
		if (orphanSourceRepository != null) {
			this.getJpaTemplate().remove(orphanSourceRepository);
		}
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#deleteProject(org.programmerplanet.intracollab.model.Project)
	 */
	public void deleteProject(Project project) {
		project = this.getJpaTemplate().getReference(Project.class, project.getId());
		this.getJpaTemplate().remove(project);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#isProjectNameUnique(java.lang.Long, java.lang.String)
	 */
	public boolean isProjectNameUnique(Long id, String name) {
		String query = "SELECT COUNT(*) FROM Project WHERE name = :name AND id <> :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("id", id != null ? id : Integer.valueOf(0));
		SingleResultJpaCallback callback = new SingleResultJpaCallback(query, params);
		Number count = (Number)this.getJpaTemplate().execute(callback);
		return count.intValue() == 0;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getTickets(org.programmerplanet.intracollab.model.Project)
	 */
	public Collection<Ticket> getTickets(Project project) {
		String query = "SELECT t FROM Ticket AS t WHERE t.project = :project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		List list = this.getJpaTemplate().findByNamedParams(query, params);
		return list;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getClosedTickets(org.programmerplanet.intracollab.model.Project)
	 */
	public Collection<Ticket> getClosedTickets(Project project) {
		String query = "SELECT t FROM Ticket AS t WHERE t.project = :project AND t.status = :status";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("status", Ticket.Status.COMPLETED);
		List list = this.getJpaTemplate().findByNamedParams(query, params);
		return list;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getOpenTickets(org.programmerplanet.intracollab.model.Project)
	 */
	public Collection<Ticket> getOpenTickets(Project project) {
		String query = "SELECT t FROM Ticket AS t WHERE t.project = :project AND t.status <> :status";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("status", Ticket.Status.COMPLETED);
		List list = this.getJpaTemplate().findByNamedParams(query, params);
		return list;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getTickets(org.programmerplanet.intracollab.model.Milestone)
	 */
	public Collection<Ticket> getTickets(Milestone milestone) {
		String query = "SELECT t FROM Ticket AS t WHERE t.milestone = :milestone";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("milestone", milestone);
		List list = this.getJpaTemplate().findByNamedParams(query, params);
		return list;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getTicket(java.lang.Long)
	 */
	public Ticket getTicket(Long id) {
		return this.getJpaTemplate().find(Ticket.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getTicket(java.lang.Long, java.lang.String[])
	 */
	public Ticket getTicket(Long id, String... fetches) {
		return getWithFetches(Ticket.class, id, fetches);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveTicket(org.programmerplanet.intracollab.model.Ticket, org.programmerplanet.intracollab.model.User)
	 */
	public void saveTicket(Ticket ticket, User user) {
		TicketChange change = null;
		boolean newTicket = (ticket.getId() == null);
		if (!newTicket) {
			Ticket oldTicket = getTicket(ticket.getId());
			change = TicketChange.calculateTicketChange(oldTicket, ticket, user);
		}
		ticket = this.getJpaTemplate().merge(ticket);
		if (newTicket) {
			notificationManager.sendTicketCreateNotification(ticket);
		} else if (change != null) {
			this.getJpaTemplate().merge(change);
			notificationManager.sendTicketChangeNotification(change);
		}
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#deleteTicket(org.programmerplanet.intracollab.model.Ticket)
	 */
	public void deleteTicket(Ticket ticket) {
		ticket = this.getJpaTemplate().getReference(Ticket.class, ticket.getId());
		this.getJpaTemplate().remove(ticket);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveComment(org.programmerplanet.intracollab.model.Comment)
	 */
	public void saveComment(Comment comment) {
		this.getJpaTemplate().merge(comment);
		notificationManager.sendCommentNotification(comment);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getAttachment(java.lang.Long)
	 */
	public Attachment getAttachment(Long id) {
		return this.getJpaTemplate().find(Attachment.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveAttachment(org.programmerplanet.intracollab.model.Attachment)
	 */
	public void saveAttachment(Attachment attachment) {
		this.getJpaTemplate().merge(attachment);
		notificationManager.sendAttachmentNotification(attachment);
	}

	private <T> T getWithFetches(Class<T> entityClass, Long id, String... fetches) {
		String query = "SELECT e FROM " + entityClass.getName() + " AS e ";
		for (String fetch : fetches) {
			query += "LEFT JOIN FETCH e." + fetch + " ";
		}
		query += "WHERE e.id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		SingleResultJpaCallback callback = new SingleResultJpaCallback(query, params);
		T entity = (T)this.getJpaTemplate().execute(callback);
		return entity;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getComponent(java.lang.Long)
	 */
	public Component getComponent(Long id) {
		return this.getJpaTemplate().find(Component.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveComponent(org.programmerplanet.intracollab.model.Component)
	 */
	public void saveComponent(Component component) {
		this.getJpaTemplate().merge(component);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#deleteComponent(org.programmerplanet.intracollab.model.Component)
	 */
	public void deleteComponent(Component component) {
		component = this.getJpaTemplate().getReference(Component.class, component.getId());
		this.getJpaTemplate().remove(component);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getMilestone(java.lang.Long)
	 */
	public Milestone getMilestone(Long id) {
		return this.getJpaTemplate().find(Milestone.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveMilestone(org.programmerplanet.intracollab.model.Milestone)
	 */
	public void saveMilestone(Milestone milestone) {
		this.getJpaTemplate().merge(milestone);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#deleteMilestone(org.programmerplanet.intracollab.model.Milestone)
	 */
	public void deleteMilestone(Milestone milestone) {
		milestone = this.getJpaTemplate().getReference(Milestone.class, milestone.getId());
		this.getJpaTemplate().remove(milestone);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getSourceRepositories()
	 */
	public Collection<SourceRepository> getSourceRepositories() {
		return this.getJpaTemplate().find("SELECT e FROM SourceRepository AS e");
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#saveRepositoryChanges(org.programmerplanet.intracollab.model.SourceRepository, java.util.Collection)
	 */
	public void saveRepositoryChanges(SourceRepository sourceRepository, Collection<RepositoryChange> repositoryChanges) {
		for (RepositoryChange repositoryChange : repositoryChanges) {
			this.getJpaTemplate().merge(repositoryChange);
		}
		this.getJpaTemplate().merge(sourceRepository);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getTicketsCreatedBetween(org.programmerplanet.intracollab.model.Project, org.programmerplanet.intracollab.util.DateRange)
	 */
	public Collection<Ticket> getTicketsCreatedBetween(Project project, DateRange dateRange) {
		String query = "SELECT t FROM Ticket AS t WHERE t.project = :project AND t.created BETWEEN :startDate AND :endDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("startDate", dateRange.getStart());
		params.put("endDate", dateRange.getEnd());
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getTicketChangesCreatedBetween(org.programmerplanet.intracollab.model.Project, org.programmerplanet.intracollab.util.DateRange)
	 */
	public Collection<TicketChange> getTicketChangesCreatedBetween(Project project, DateRange dateRange) {
		String query = "SELECT tc FROM TicketChange AS tc WHERE tc.ticket.project = :project AND tc.changeDate BETWEEN :startDate AND :endDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("startDate", dateRange.getStart());
		params.put("endDate", dateRange.getEnd());
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getRepositoryChangesCreatedBetween(org.programmerplanet.intracollab.model.Project, org.programmerplanet.intracollab.util.DateRange)
	 */
	public Collection<RepositoryChange> getRepositoryChangesCreatedBetween(Project project, DateRange dateRange) {
		String query = "SELECT rc FROM RepositoryChange AS rc WHERE rc.project = :project AND rc.changeDate BETWEEN :startDate AND :endDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("startDate", dateRange.getStart());
		params.put("endDate", dateRange.getEnd());
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getCommentsCreatedBetween(org.programmerplanet.intracollab.model.Project, org.programmerplanet.intracollab.util.DateRange)
	 */
	public Collection<Comment> getCommentsCreatedBetween(Project project, DateRange dateRange) {
		String query = "SELECT c FROM Comment AS c WHERE c.ticket.project = :project AND c.created BETWEEN :startDate AND :endDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("startDate", dateRange.getStart());
		params.put("endDate", dateRange.getEnd());
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getAttachmentsCreatedBetween(org.programmerplanet.intracollab.model.Project, org.programmerplanet.intracollab.util.DateRange)
	 */
	public Collection<AttachmentInfo> getAttachmentsCreatedBetween(Project project, DateRange dateRange) {
		String query = "SELECT a FROM AttachmentInfo AS a WHERE a.ticket.project = :project AND a.created BETWEEN :startDate AND :endDate";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		params.put("startDate", dateRange.getStart());
		params.put("endDate", dateRange.getEnd());
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getRepositoryChange(java.lang.Long)
	 */
	public RepositoryChange getRepositoryChange(Long id) {
		return this.getJpaTemplate().find(RepositoryChange.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#getRepositoryChange(java.lang.Long, java.lang.String[])
	 */
	public RepositoryChange getRepositoryChange(Long id, String... fetches) {
		return getWithFetches(RepositoryChange.class, id, fetches);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.ProjectManager#search(java.lang.String)
	 */
	public List<SearchResult> search(String search) {
		List<SearchResult> searchResults = new LinkedList<SearchResult>();

		Collection<Ticket> tickets = searchTickets(search);
		for (Ticket ticket : tickets) {
			searchResults.add(new TicketSearchResult(ticket));
		}

		Collection<AttachmentInfo> attachments = searchAttachments(search);
		for (AttachmentInfo attachment : attachments) {
			searchResults.add(new AttachmentSearchResult(attachment));
		}

		Collection<Comment> comments = searchComments(search);
		for (Comment comment : comments) {
			searchResults.add(new CommentSearchResult(comment));
		}

		Collection<RepositoryChange> repositoryChanges = searchRepositoryChanges(search);
		for (RepositoryChange repositoryChange : repositoryChanges) {
			searchResults.add(new RepositoryChangeSearchResult(repositoryChange));
		}

		Collections.sort(searchResults, new PropertyComparator("date", false, false));
		return searchResults;
	}

	private Collection<Ticket> searchTickets(String search) {
		String query = "SELECT t FROM Ticket AS t WHERE t.name LIKE :search OR t.description LIKE :search";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", "%" + search + "%");
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	private Collection<AttachmentInfo> searchAttachments(String search) {
		String query = "SELECT a FROM AttachmentInfo AS a WHERE a.description LIKE :search";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", "%" + search + "%");
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	private Collection<Comment> searchComments(String search) {
		String query = "SELECT c FROM Comment AS c WHERE c.content LIKE :search";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", "%" + search + "%");
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

	private Collection<RepositoryChange> searchRepositoryChanges(String search) {
		String query = "SELECT rc FROM RepositoryChange AS rc WHERE rc.comment LIKE :search";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("search", "%" + search + "%");
		return this.getJpaTemplate().findByNamedParams(query, params);
	}

}
