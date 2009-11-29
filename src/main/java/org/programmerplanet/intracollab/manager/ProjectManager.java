package org.programmerplanet.intracollab.manager;

import java.util.Collection;

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
import org.programmerplanet.intracollab.util.DateRange;

/**
 * Primary interface for managing projects.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public interface ProjectManager {

	Collection<Project> getProjects();

	Project getProject(Long id);

	Project getProject(Long id, String... fetches);

	void saveProject(Project project);

	void deleteProject(Project project);

	boolean isProjectNameUnique(Long id, String name);

	Collection<Ticket> getTickets(Project project);

	Collection<Ticket> getTickets(Milestone milestone);

	Ticket getTicket(Long id);

	Ticket getTicket(Long id, String... fetches);

	void saveTicket(Ticket ticket, User user);

	void deleteTicket(Ticket ticket);

	void saveComment(Comment comment);

	Attachment getAttachment(Long id);

	void saveAttachment(Attachment attachment);

	Component getComponent(Long id);

	void saveComponent(Component component);

	void deleteComponent(Component component);

	Milestone getMilestone(Long id);

	void saveMilestone(Milestone milestone);

	void deleteMilestone(Milestone milestone);

	Collection<SourceRepository> getSourceRepositories();

	void saveRepositoryChanges(SourceRepository sourceRepository, Collection<RepositoryChange> repositoryChanges);

	Collection<Ticket> getTicketsCreatedBetween(Project project, DateRange dateRange);

	Collection<TicketChange> getTicketChangesCreatedBetween(Project project, DateRange dateRange);

	Collection<RepositoryChange> getRepositoryChangesCreatedBetween(Project project, DateRange dateRange);

	Collection<Comment> getCommentsCreatedBetween(Project project, DateRange dateRange);

	Collection<AttachmentInfo> getAttachmentsCreatedBetween(Project project, DateRange dateRange);

	RepositoryChange getRepositoryChange(Long id);

	RepositoryChange getRepositoryChange(Long id, String... fetches);

}
