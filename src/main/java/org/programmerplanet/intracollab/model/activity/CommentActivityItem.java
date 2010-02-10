package org.programmerplanet.intracollab.model.activity;

import java.util.Date;

import org.programmerplanet.intracollab.model.Comment;

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
	 * @see org.programmerplanet.intracollab.model.activity.ActivityItem#getDate()
	 */
	public Date getDate() {
		return comment.getCreated();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.activity.ActivityItem#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + comment.getTicket().getId() + " commented on by " + comment.getCreatedBy();
	}

}
