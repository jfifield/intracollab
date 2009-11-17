package org.programmerplanet.intracollab.manager;

import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.TicketChange;
import org.programmerplanet.intracollab.model.TicketChangeField;
import org.programmerplanet.intracollab.model.User;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class JpaProjectManagerTest extends TestCase {

	private static long SECOND = 1000;
	private static long MINUTE = SECOND * 60;

	public void testSaveTicket_NewTicket() {
		Ticket newTicket = new Ticket();

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.merge(newTicket)).andReturn(null);
		EasyMock.replay(entityManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);

		User user = new User();
		user.setUsername("TestUser");

		projectManager.saveTicket(newTicket, user);
	}

	public void testSaveTicket_NoChanges() {
		Long id = new Long(1);

		Ticket oldTicket = new Ticket();
		oldTicket.setId(id);

		Ticket newTicket = new Ticket();
		newTicket.setId(id);

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.find(Ticket.class, id)).andReturn(oldTicket);
		EasyMock.expect(entityManager.merge(newTicket)).andReturn(null);
		EasyMock.replay(entityManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);

		User user = new User();
		user.setUsername("TestUser");

		projectManager.saveTicket(newTicket, user);
	}

	public void testSaveTicket_Changes() {
		Long id = new Long(1);

		Ticket oldTicket = new Ticket();
		oldTicket.setId(id);
		oldTicket.setName("Old Ticket Name");

		Ticket newTicket = new Ticket();
		newTicket.setId(id);
		newTicket.setName("New Ticket Name");

		Capture<TicketChange> capturedArgument = new Capture<TicketChange>();

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.find(Ticket.class, id)).andReturn(oldTicket);
		EasyMock.expect(entityManager.merge(newTicket)).andReturn(null);
		EasyMock.expect(entityManager.merge(EasyMock.capture(capturedArgument))).andReturn(null);
		EasyMock.replay(entityManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);

		User user = new User();
		user.setUsername("TestUser");

		projectManager.saveTicket(newTicket, user);

		TicketChange change = capturedArgument.getValue();
		assertNotNull("change is null", change);
		assertSame("change.ticket", newTicket, change.getTicket());
		assertEquals("change.username", "TestUser", change.getUsername());
		assertTrue("change.changeDate", change.getChangeDate().getTime() >= System.currentTimeMillis() - MINUTE);

		Set<TicketChangeField> fields = change.getFields();
		assertEquals("# changes", 1, fields.size());

		TicketChangeField nameField = (TicketChangeField)CollectionUtils.find(fields, new BeanPropertyValueEqualsPredicate("field", "Name"));
		assertNotNull("nameField is null", nameField);
		assertSame("nameField.ticketChange", change, nameField.getTicketChange());
		assertEquals("nameField.oldValue", "Old Ticket Name", nameField.getOldValue());
		assertEquals("nameField.newValue", "New Ticket Name", nameField.getNewValue());
	}

}
