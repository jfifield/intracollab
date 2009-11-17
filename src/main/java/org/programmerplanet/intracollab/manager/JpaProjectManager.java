package org.programmerplanet.intracollab.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.programmerplanet.intracollab.model.Attachment;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Component;
import org.programmerplanet.intracollab.model.Milestone;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.TicketChange;
import org.programmerplanet.intracollab.model.User;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * JPA implementation of the <code>ProjectManager</code> interface.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class JpaProjectManager extends JpaDaoSupport implements ProjectManager {

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
		this.getJpaTemplate().merge(project);
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
		if (ticket.getId() != null) {
			Ticket oldTicket = getTicket(ticket.getId());
			change = TicketChange.calculateTicketChange(oldTicket, ticket, user);
		}
		this.getJpaTemplate().merge(ticket);
		if (change != null) {
			this.getJpaTemplate().merge(change);
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

}
