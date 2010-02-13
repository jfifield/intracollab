package org.programmerplanet.intracollab.web.ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.util.Pair;
import org.programmerplanet.intracollab.web.ServletRequestUtils;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketListController implements Controller {

	private static final String SHOW_ALL = "all";
	private static final String SHOW_OPEN = "open";
	private static final String SHOW_CLOSED = "closed";

	private static final List<Pair<String, String>> SHOW_OPTIONS = new ArrayList<Pair<String, String>>() {
		{
			add(new Pair<String, String>(SHOW_ALL, StringUtils.capitalize(SHOW_ALL)));
			add(new Pair<String, String>(SHOW_OPEN, StringUtils.capitalize(SHOW_OPEN)));
			add(new Pair<String, String>(SHOW_CLOSED, StringUtils.capitalize(SHOW_CLOSED)));
		}
	};

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long projectId = ServletRequestUtils.getLongParameter(request, "project_id");
		Project project = projectId != null ? projectManager.getProject(projectId) : null;

		Collection<Ticket> tickets = null;
		String show = request.getParameter("show");
		if (SHOW_ALL.equals(show)) {
			tickets = project != null ? projectManager.getTickets(project) : projectManager.getTickets();
		} else if (SHOW_CLOSED.equals(show)) {
			tickets = project != null ? projectManager.getClosedTickets(project) : projectManager.getClosedTickets();
		} else {
			show = SHOW_OPEN;
			tickets = project != null ? projectManager.getOpenTickets(project) : projectManager.getOpenTickets();
		}

		List<Project> projects = new LinkedList<Project>(projectManager.getProjects());
		Collections.sort(projects, new PropertyComparator("name", false, true));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("project", project);
		model.put("projects", projects);
		model.put("tickets", tickets);
		model.put("show", show);
		model.put("showOptions", SHOW_OPTIONS);
		return new ModelAndView("ticket/list", model);
	}

}
