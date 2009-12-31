package org.programmerplanet.intracollab.model;

/**
 * Represents a single component (or module, subsystem) of a project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class Component extends BaseEntity {

	private Project project;
	private String name;

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

}
