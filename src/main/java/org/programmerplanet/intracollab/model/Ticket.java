package org.programmerplanet.intracollab.model;

import java.util.Set;
import java.util.Date;

/**
 * Represents a ticket within a project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class Ticket extends BaseEntity {

	private Project project;
	private String name;
	private String description;
	private Component component;
	private Milestone milestone;
	private Priority priority;
	private Status status;
	private User assignedTo;
	private Date created;
	private String createdBy;
	private Set<Comment> comments;
	private Set<AttachmentInfo> attachments;
	private Set<TicketChange> ticketChanges;
	private Set<RepositoryChange> repositoryChanges;

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Component getComponent() {
		return component;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setAttachments(Set<AttachmentInfo> attachments) {
		this.attachments = attachments;
	}

	public Set<AttachmentInfo> getAttachments() {
		return attachments;
	}

	public void setTicketChanges(Set<TicketChange> ticketChanges) {
		this.ticketChanges = ticketChanges;
	}

	public Set<TicketChange> getTicketChanges() {
		return ticketChanges;
	}

	public void setRepositoryChanges(Set<RepositoryChange> repositoryChanges) {
		this.repositoryChanges = repositoryChanges;
	}

	public Set<RepositoryChange> getRepositoryChanges() {
		return repositoryChanges;
	}

	public static enum Priority {
		LOW("Low"), NORMAL("Normal"), HIGH("High");

		private String title;

		private Priority(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

	}

	public static enum Status {
		NEW("New"), ACTIVE("Active"), COMPLETED("Completed");

		private String title;

		private Status(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

	}

}
