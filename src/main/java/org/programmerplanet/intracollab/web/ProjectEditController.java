package org.programmerplanet.intracollab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.web.spring.SimpleMultiActionFormController;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ProjectEditController extends SimpleMultiActionFormController {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Project project = (Project) command;
		projectManager.saveProject(project);
		return new ModelAndView(this.getSuccessView());
	}

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Project project = (Project) command;
		projectManager.deleteProject(project);
		return new ModelAndView(this.getSuccessView());
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		return new ModelAndView(this.getSuccessView());
	}

	/**
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#suppressBinding(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean suppressBinding(HttpServletRequest request) {
		return !WebUtils.hasSubmitParameter(request, "__save");
	}

	/**
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		if (id != null) {
			return projectManager.getProject(id);
		} else {
			return new Project();
		}
	}

}
