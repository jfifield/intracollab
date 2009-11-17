package org.programmerplanet.intracollab.model;

import java.util.Set;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class TicketChangeTest extends TestCase {

	private static long SECOND = 1000;
	private static long MINUTE = SECOND * 60;

	public void testCalculateTicketChange_NoChanges() {
		Ticket oldTicket = new Ticket();

		Ticket newTicket = new Ticket();

		User user = new User();

		TicketChange change = TicketChange.calculateTicketChange(oldTicket, newTicket, user);
		assertNull("change is not null", change);
	}

	public void testCalculateTicketChange_Changes() {
		Ticket oldTicket = new Ticket();
		oldTicket.setName("Old Ticket Name");
		oldTicket.setDescription("Old Ticket Description");
		Component oldComponent = new Component();
		oldComponent.setName("Old Ticket Component");
		oldTicket.setComponent(oldComponent);
		Milestone oldMilestone = new Milestone();
		oldMilestone.setName("Old Ticket Milestone");
		oldTicket.setMilestone(oldMilestone);
		oldTicket.setPriority(Ticket.Priority.NORMAL);
		oldTicket.setStatus(Ticket.Status.NEW);
		User oldUser = new User();
		oldUser.setUsername("OldUser");
		oldTicket.setAssignedTo(oldUser);

		Ticket newTicket = new Ticket();
		newTicket.setName("New Ticket Name");
		newTicket.setDescription("New Ticket Description");
		Component newComponent = new Component();
		newComponent.setName("New Ticket Component");
		newTicket.setComponent(newComponent);
		Milestone newMilestone = new Milestone();
		newMilestone.setName("New Ticket Milestone");
		newTicket.setMilestone(newMilestone);
		newTicket.setPriority(Ticket.Priority.HIGH);
		newTicket.setStatus(Ticket.Status.ACTIVE);
		User newUser = new User();
		newUser.setUsername("NewUser");
		newTicket.setAssignedTo(newUser);

		User user = new User();
		user.setUsername("TestUser");

		TicketChange change = TicketChange.calculateTicketChange(oldTicket, newTicket, user);
		assertNotNull("change is null", change);
		assertSame("change.ticket", newTicket, change.getTicket());
		assertEquals("change.username", "TestUser", change.getUsername());
		assertTrue("change.changeDate", change.getChangeDate().getTime() >= System.currentTimeMillis() - MINUTE);

		Set<TicketChangeField> fields = change.getFields();
		assertEquals("# changes", 7, fields.size());

		TicketChangeField nameField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Name"));
		assertNotNull("nameField is null", nameField);
		assertSame("nameField.ticketChange", change, nameField.getTicketChange());
		assertEquals("nameField.oldValue", "Old Ticket Name", nameField.getOldValue());
		assertEquals("nameField.newValue", "New Ticket Name", nameField.getNewValue());

		TicketChangeField descriptionField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Description"));
		assertNotNull("descriptionField is null", descriptionField);
		assertSame("descriptionField.ticketChange", change, descriptionField.getTicketChange());
		assertEquals("descriptionField.oldValue", "Old Ticket Description", descriptionField.getOldValue());
		assertEquals("descriptionField.newValue", "New Ticket Description", descriptionField.getNewValue());

		TicketChangeField componentField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Component"));
		assertNotNull("componentField is null", componentField);
		assertSame("componentField.ticketChange", change, componentField.getTicketChange());
		assertEquals("componentField.oldValue", "Old Ticket Component", componentField.getOldValue());
		assertEquals("componentField.newValue", "New Ticket Component", componentField.getNewValue());

		TicketChangeField milestoneField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Milestone"));
		assertNotNull("milestoneField is null", milestoneField);
		assertSame("milestoneField.ticketChange", change, milestoneField.getTicketChange());
		assertEquals("milestoneField.oldValue", "Old Ticket Milestone", milestoneField.getOldValue());
		assertEquals("milestoneField.newValue", "New Ticket Milestone", milestoneField.getNewValue());

		TicketChangeField priorityField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Priority"));
		assertNotNull("priorityField is null", priorityField);
		assertSame("priorityField.ticketChange", change, priorityField.getTicketChange());
		assertEquals("priorityField.oldValue", "Normal", priorityField.getOldValue());
		assertEquals("priorityField.newValue", "High", priorityField.getNewValue());

		TicketChangeField statusField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Status"));
		assertNotNull("statusField is null", statusField);
		assertSame("statusField.ticketChange", change, statusField.getTicketChange());
		assertEquals("statusField.oldValue", "New", statusField.getOldValue());
		assertEquals("statusField.newValue", "Active", statusField.getNewValue());

		TicketChangeField assignedToField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Assigned To"));
		assertNotNull("assignedToField is null", assignedToField);
		assertSame("assignedToField.ticketChange", change, assignedToField.getTicketChange());
		assertEquals("assignedToField.oldValue", "OldUser", assignedToField.getOldValue());
		assertEquals("assignedToField.newValue", "NewUser", assignedToField.getNewValue());
	}

}
