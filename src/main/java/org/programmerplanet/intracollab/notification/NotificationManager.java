package org.programmerplanet.intracollab.notification;

import org.programmerplanet.intracollab.model.Attachment;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.TicketChange;

/**
 * Primary interface for sending email notifications of activity against
 * tickets.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public interface NotificationManager {

	void sendTicketCreateNotification(Ticket ticket);

	void sendTicketChangeNotification(TicketChange ticketChange);

	void sendCommentNotification(Comment comment);

	void sendAttachmentNotification(Attachment attachment);

}