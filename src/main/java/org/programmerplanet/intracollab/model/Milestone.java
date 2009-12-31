package org.programmerplanet.intracollab.model;

import java.util.Date;

/**
 * Represents a milestone of a project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class Milestone extends BaseEntity {

	private Project project;
	private String name;
	private Date dueDate;
	private boolean completed;

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

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isCompleted() {
		return completed;
	}

}
