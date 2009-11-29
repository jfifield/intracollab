package org.programmerplanet.intracollab.web.ticket;

import org.programmerplanet.intracollab.model.Ticket;
import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketValidatorTest extends TestCase {

	public void testValidate_Good() {
		TicketValidator validator = new TicketValidator();

		Ticket ticket = new Ticket();
		ticket.setName("Test Name");
		ticket.setPriority(Ticket.Priority.NORMAL);
		ticket.setStatus(Ticket.Status.NEW);

		BindException errors = new BindException(ticket, "ticket");

		validator.validate(ticket, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingName() {
		TicketValidator validator = new TicketValidator();

		Ticket ticket = new Ticket();
		ticket.setName(null);
		ticket.setPriority(Ticket.Priority.NORMAL);
		ticket.setStatus(Ticket.Status.NEW);

		BindException errors = new BindException(ticket, "ticket");

		validator.validate(ticket, errors);

		assertTrue("errors", errors.hasFieldErrors("name"));
	}

	public void testValidate_MissingPriority() {
		TicketValidator validator = new TicketValidator();

		Ticket ticket = new Ticket();
		ticket.setName("Test Name");
		ticket.setPriority(null);
		ticket.setStatus(Ticket.Status.NEW);

		BindException errors = new BindException(ticket, "ticket");

		validator.validate(ticket, errors);

		assertTrue("errors", errors.hasFieldErrors("priority"));
	}

	public void testValidate_MissingStatus() {
		TicketValidator validator = new TicketValidator();

		Ticket ticket = new Ticket();
		ticket.setName("Test Name");
		ticket.setPriority(Ticket.Priority.NORMAL);
		ticket.setStatus(null);

		BindException errors = new BindException(ticket, "ticket");

		validator.validate(ticket, errors);

		assertTrue("errors", errors.hasFieldErrors("status"));
	}

}
