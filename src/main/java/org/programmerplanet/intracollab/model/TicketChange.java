package org.programmerplanet.intracollab.model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NullValueInNestedPathException;

/**
 * Represents a change to one or more fields on a ticket.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketChange extends BaseEntity {

	private Ticket ticket;
	private String username;
	private Date changeDate;
	private Set<TicketChangeField> fields;

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setFields(Set<TicketChangeField> fields) {
		this.fields = fields;
	}

	public Set<TicketChangeField> getFields() {
		return fields;
	}

	public static TicketChange calculateTicketChange(Ticket oldTicket, Ticket newTicket, User user) {
		TicketChange change = new TicketChange();
		change.setTicket(newTicket);
		change.setUsername(user.getUsername());
		change.setChangeDate(new Date());
		change.setFields(new HashSet<TicketChangeField>());

		BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldTicket);
		BeanWrapper newBeanWrapper = new BeanWrapperImpl(newTicket);

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("name", "Name");
		properties.put("description", "Description");
		properties.put("component.name", "Component");
		properties.put("milestone.name", "Milestone");
		properties.put("priority.title", "Priority");
		properties.put("status.title", "Status");
		properties.put("assignedTo.username", "Assigned To");

		for (Map.Entry<String, String> property : properties.entrySet()) {
			String propertyName = property.getKey();
			String fieldName = property.getValue();
			String oldValue = null;
			try {
				oldValue = (String)oldBeanWrapper.getPropertyValue(propertyName);
			} catch (NullValueInNestedPathException e) {
				// ignore
			}
			String newValue = null;
			try {
				newValue = (String)newBeanWrapper.getPropertyValue(propertyName);
			} catch (NullValueInNestedPathException e) {
				// ignore
			}

			if (!ObjectUtils.equals(newValue, oldValue)) {
				TicketChangeField field = new TicketChangeField();
				field.setTicketChange(change);
				field.setField(fieldName);
				field.setOldValue(oldValue);
				field.setNewValue(newValue);
				change.getFields().add(field);
			}
		}

		if (change.getFields().isEmpty()) {
			change = null;
		}

		return change;
	}

}
