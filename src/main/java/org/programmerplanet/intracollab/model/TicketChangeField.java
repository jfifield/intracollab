package org.programmerplanet.intracollab.model;

/**
 * Represents a change to a single field on a ticket.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketChangeField {

	private Long id;
	private TicketChange ticketChange;
	private String field;
	private String oldValue;
	private String newValue;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTicketChange(TicketChange ticketChange) {
		this.ticketChange = ticketChange;
	}

	public TicketChange getTicketChange() {
		return ticketChange;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getNewValue() {
		return newValue;
	}

}
