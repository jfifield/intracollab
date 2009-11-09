package org.programmerplanet.intracollab.web;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.manager.UserManager;
import org.programmerplanet.intracollab.model.Component;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.User;
import org.programmerplanet.intracollab.propertyeditors.ComponentPropertyEditor;
import org.programmerplanet.intracollab.propertyeditors.UserPropertyEditor;
import org.programmerplanet.intracollab.web.spring.SimpleMultiActionFormController;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
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
public class TicketEditController extends SimpleMultiActionFormController {

	private ProjectManager projectManager;
	private UserManager userManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder(javax.servlet.http.HttpServletRequest, org.springframework.web.bind.ServletRequestDataBinder)
	 */
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(User.class, new UserPropertyEditor(userManager));
		binder.registerCustomEditor(Component.class, new ComponentPropertyEditor(projectManager));
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Ticket ticket = (Ticket)command;
		projectManager.saveTicket(ticket);
		return new ModelAndView(this.getSuccessView(), "project_id", ticket.getProject().getId());
	}

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Ticket ticket = (Ticket)command;
		projectManager.deleteTicket(ticket);
		return new ModelAndView(this.getSuccessView(), "project_id", ticket.getProject().getId());
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Ticket ticket = (Ticket)command;
		return new ModelAndView(this.getSuccessView(), "project_id", ticket.getProject().getId());
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
			return projectManager.getTicket(id);
		} else {
			Ticket ticket = new Ticket();
			Long projectId = ServletRequestUtils.getLongParameter(request, "project_id");
			Project project = projectManager.getProject(projectId);
			ticket.setProject(project);
			ticket.setCreated(new Date());
			UserSession userSession = UserSession.getUserSession(request);
			User user = userSession.getUser();
			ticket.setCreatedBy(user.getUsername());
			return ticket;
		}
	}

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("priorityValues", Ticket.Priority.values());
		data.put("statusValues", Ticket.Status.values());

		Collection<User> users = userManager.getUsers();
		data.put("users", users);

		Ticket ticket = (Ticket)command;
		Project project = projectManager.getProject(ticket.getProject().getId(), "components");
		data.put("components", project.getComponents());

		return data;
	}

}
