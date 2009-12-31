package org.programmerplanet.intracollab.model;

/**
 * Represents a change to a single file in the source repository.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class RepositoryChangeFile extends BaseEntity {

	private RepositoryChange repositoryChange;
	private String filename;
	private String revision;

	public void setRepositoryChange(RepositoryChange repositoryChange) {
		this.repositoryChange = repositoryChange;
	}

	public RepositoryChange getRepositoryChange() {
		return repositoryChange;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getRevision() {
		return revision;
	}

}
