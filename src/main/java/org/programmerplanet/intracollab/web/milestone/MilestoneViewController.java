package org.programmerplanet.intracollab.web.milestone;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Milestone;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.web.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class MilestoneViewController implements Controller {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		Milestone milestone = projectManager.getMilestone(id);
		Collection<Ticket> tickets = projectManager.getTickets(milestone);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("milestone", milestone);
		model.put("tickets", tickets);
		return new ModelAndView("milestone/view", model);
	}

}