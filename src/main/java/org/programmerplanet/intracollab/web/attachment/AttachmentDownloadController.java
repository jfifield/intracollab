package org.programmerplanet.intracollab.web.attachment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Attachment;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AttachmentDownloadController implements Controller {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = ServletRequestUtils.getLongParameter(request, "id");

		Attachment attachment = projectManager.getAttachment(id);

		response.setHeader("Content-Type", attachment.getContentType());
		response.setHeader("Content-Length", Long.toString(attachment.getFileSize()));
		response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getFileName());

		ServletOutputStream outputStream = response.getOutputStream();
		IOUtils.write(attachment.getContent(), outputStream);
		response.flushBuffer();

		return null;
	}

}
