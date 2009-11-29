package org.programmerplanet.intracollab.web.admin.project;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Project;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ProjectValidator implements Validator {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(Project.class);
	}

	public void validate(Object obj, Errors errors) {
		Project project = (Project)obj;

		// name is required
		if (StringUtils.isEmpty(project.getName())) {
			errors.rejectValue("name", "error.required");
		}
		// name must be unique
		else if (!projectManager.isProjectNameUnique(project.getId(), project.getName())) {
			errors.rejectValue("name", "error.project.name.exists");
		}
	}

}
