package org.programmerplanet.intracollab.model;

import java.util.List;

/**
 * Represents a source repository for the project.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public abstract class SourceRepository extends BaseEntity {

	private Project project;
	private String path;
	private String modules;
	private Long lastChangePoint;

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	public String getModules() {
		return modules;
	}

	public void setLastChangePoint(Long lastChangePoint) {
		this.lastChangePoint = lastChangePoint;
	}

	public Long getLastChangePoint() {
		return lastChangePoint;
	}

	public abstract List<RepositoryChange> getRepositoryChanges();

	public abstract Long getLastChangePoint(List<RepositoryChange> repositoryChanges);

}
