package org.programmerplanet.intracollab.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.User;
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
public class CommentEditController extends SimpleMultiActionFormController {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Comment comment = (Comment)command;
		projectManager.saveComment(comment);
		return new ModelAndView(this.getSuccessView(), "id", comment.getTicket().getId());
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Comment comment = (Comment)command;
		return new ModelAndView(this.getSuccessView(), "id", comment.getTicket().getId());
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
		Comment comment = new Comment();
		Long ticketId = ServletRequestUtils.getLongParameter(request, "ticket_id");
		Ticket ticket = projectManager.getTicket(ticketId);
		comment.setTicket(ticket);
		comment.setCreated(new Date());
		UserSession userSession = UserSession.getUserSession(request);
		User user = userSession.getUser();
		comment.setCreatedBy(user.getUsername());
		return comment;
	}

}
