package org.programmerplanet.intracollab.web.comment;

import java.util.Date;

import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.web.project.ActivityItem;

/**
 * An <code>ActivityItem</code> for a <code>Comment</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class CommentActivityItem implements ActivityItem {

	private Comment comment;

	public CommentActivityItem(Comment comment) {
		this.comment = comment;
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDate()
	 */
	public Date getDate() {
		return comment.getCreated();
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + comment.getTicket().getId() + " commented on by " + comment.getCreatedBy();
	}

}
