package org.programmerplanet.intracollab.web;

import java.util.Date;

import org.programmerplanet.intracollab.model.AttachmentInfo;

/**
 * An <code>ActivityItem</code> for an <code>Attachment</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AttachmentActivityItem implements ActivityItem {

	private AttachmentInfo attachment;

	public AttachmentActivityItem(AttachmentInfo attachment) {
		this.attachment = attachment;
	}

	/**
	 * @see org.programmerplanet.intracollab.web.ActivityItem#getDate()
	 */
	public Date getDate() {
		return attachment.getCreated();
	}

	/**
	 * @see org.programmerplanet.intracollab.web.ActivityItem#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + attachment.getTicket().getId() + " attachment created by " + attachment.getCreatedBy();
	}

}
