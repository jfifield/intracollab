package org.programmerplanet.intracollab.manager;

import java.util.Collection;

import org.programmerplanet.intracollab.model.Attachment;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Component;
import org.programmerplanet.intracollab.model.Milestone;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.Ticket;

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

	Ticket getTicket(Long id);

	Ticket getTicket(Long id, String... fetches);

	void saveTicket(Ticket ticket);

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

}
