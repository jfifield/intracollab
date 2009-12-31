package org.programmerplanet.intracollab.web.admin.sourcerepository;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SourceRepositoryForm {

	private String type;
	private String path;
	private String modules;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
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

}
