package org.programmerplanet.intracollab.model.search;

import java.util.Date;

import org.programmerplanet.intracollab.model.AttachmentInfo;

/**
 * A <code>SearchResult</code> based on an <code>Attachment</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class AttachmentSearchResult implements SearchResult {

	private AttachmentInfo attachment;

	public AttachmentSearchResult(AttachmentInfo attachment) {
		this.attachment = attachment;
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getDate()
	 */
	public Date getDate() {
		return attachment.getCreated();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + attachment.getTicket().getId() + " attachment created by " + attachment.getCreatedBy();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getText()
	 */
	public String getText() {
		return attachment.getDescription();
	}

}
