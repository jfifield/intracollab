package org.programmerplanet.intracollab.model;

import java.util.Date;
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
	private Date lastChangeDate;

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

	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public abstract List<RepositoryChange> getRepositoryChanges();

}
