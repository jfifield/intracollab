package org.programmerplanet.intracollab.model.search;

import java.util.Date;

import org.programmerplanet.intracollab.model.Ticket;

/**
 * A <code>SearchResult</code> based on a <code>Ticket</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class TicketSearchResult implements SearchResult {

	private Ticket ticket;

	public TicketSearchResult(Ticket ticket) {
		this.ticket = ticket;
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getDate()
	 */
	public Date getDate() {
		return ticket.getCreated();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + ticket.getId() + " created by " + ticket.getCreatedBy();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getText()
	 */
	public String getText() {
		return ticket.getName() + ": " + ticket.getDescription();
	}

}
