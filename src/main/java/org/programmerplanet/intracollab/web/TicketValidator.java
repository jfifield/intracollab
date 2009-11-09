package org.programmerplanet.intracollab.web;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.model.Ticket;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(Ticket.class);
	}

	public void validate(Object obj, Errors errors) {
		Ticket ticket = (Ticket)obj;

		// name is required
		if (StringUtils.isEmpty(ticket.getName())) {
			errors.rejectValue("name", "error.required");
		}

		// priority is required
		if (ticket.getPriority() == null) {
			errors.rejectValue("priority", "error.required");
		}

		// status is required
		if (ticket.getStatus() == null) {
			errors.rejectValue("status", "error.required");
		}
	}

}
