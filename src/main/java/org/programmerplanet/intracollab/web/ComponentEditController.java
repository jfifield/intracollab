package org.programmerplanet.intracollab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.Component;
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
public class ComponentEditController extends SimpleMultiActionFormController {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Component component = (Component)command;
		projectManager.saveComponent(component);
		return new ModelAndView(this.getSuccessView(), "project_id", component.getProject().getId());
	}

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Component component = (Component)command;
		projectManager.deleteComponent(component);
		return new ModelAndView(this.getSuccessView(), "project_id", component.getProject().getId());
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Component component = (Component)command;
		return new ModelAndView(this.getSuccessView(), "project_id", component.getProject().getId());
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
			return projectManager.getComponent(id);
		} else {
			Component component = new Component();
			Long projectId = ServletRequestUtils.getLongParameter(request, "project_id");
			Project project = projectManager.getProject(projectId);
			component.setProject(project);
			return component;
		}
	}

}
