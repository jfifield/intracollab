package org.programmerplanet.intracollab.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Attachment;
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
public class AttachmentEditController extends SimpleMultiActionFormController {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		AttachmentForm attachmentForm = (AttachmentForm)command;

		Attachment attachment = new Attachment();
		Long ticketId = ServletRequestUtils.getLongParameter(request, "ticket_id");
		Ticket ticket = projectManager.getTicket(ticketId);
		attachment.setTicket(ticket);
		attachment.setCreated(new Date());
		UserSession userSession = UserSession.getUserSession(request);
		User user = userSession.getUser();
		attachment.setCreatedBy(user.getUsername());
		attachment.setDescription(attachmentForm.getDescription());
		attachment.setContent(attachmentForm.getFile().getBytes());
		attachment.setFileName(attachmentForm.getFile().getOriginalFilename());
		attachment.setFileSize(attachmentForm.getFile().getSize());
		attachment.setContentType(attachmentForm.getFile().getContentType());

		projectManager.saveAttachment(attachment);
		return new ModelAndView(this.getSuccessView(), "id", ticketId);
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Long ticketId = ServletRequestUtils.getLongParameter(request, "ticket_id");
		return new ModelAndView(this.getSuccessView(), "id", ticketId);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#suppressBinding(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean suppressBinding(HttpServletRequest request) {
		return !WebUtils.hasSubmitParameter(request, "__save");
	}

}
