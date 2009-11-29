package org.programmerplanet.intracollab.web.ticketchange;

import java.util.Date;

import org.programmerplanet.intracollab.model.TicketChange;
import org.programmerplanet.intracollab.web.project.ActivityItem;

/**
 * An <code>ActivityItem</code> for a <code>TicketChange</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketChangeActivityItem implements ActivityItem {

	private TicketChange ticketChange;

	public TicketChangeActivityItem(TicketChange ticketChange) {
		this.ticketChange = ticketChange;
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDate()
	 */
	public Date getDate() {
		return ticketChange.getChangeDate();
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDescription()
	 */
	public String getDescription() {
		return "Ticket #" + ticketChange.getTicket().getId() + " edited by " + ticketChange.getUsername();
	}

}
