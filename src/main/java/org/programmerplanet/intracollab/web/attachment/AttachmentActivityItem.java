package org.programmerplanet.intracollab.web.attachment;

import java.util.Date;

import org.programmerplanet.intracollab.model.AttachmentInfo;
import org.programmerplanet.intracollab.web.project.ActivityItem;

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
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDate()
	 */
	public Date getDate() {
		return attachment.getCreated();
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + attachment.getTicket().getId() + " attachment created by " + attachment.getCreatedBy();
	}

}
