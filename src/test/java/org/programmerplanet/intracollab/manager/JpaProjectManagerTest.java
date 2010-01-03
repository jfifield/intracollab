package org.programmerplanet.intracollab.manager;

import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.programmerplanet.intracollab.model.Attachment;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.TicketChange;
import org.programmerplanet.intracollab.model.TicketChangeField;
import org.programmerplanet.intracollab.model.User;
import org.programmerplanet.intracollab.notification.NotificationManager;

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
		EasyMock.expect(entityManager.merge(newTicket)).andReturn(newTicket);
		EasyMock.replay(entityManager);

		NotificationManager notificationManager = EasyMock.createMock(NotificationManager.class);
		notificationManager.sendTicketCreateNotification(newTicket);
		EasyMock.replay(notificationManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);
		projectManager.setNotificationManager(notificationManager);

		User user = new User();
		user.setUsername("TestUser");

		projectManager.saveTicket(newTicket, user);

		EasyMock.verify(entityManager);
		EasyMock.verify(notificationManager);
	}

	public void testSaveTicket_NoChanges() {
		Long id = new Long(1);

		Ticket oldTicket = new Ticket();
		oldTicket.setId(id);

		Ticket newTicket = new Ticket();
		newTicket.setId(id);

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.find(Ticket.class, id)).andReturn(oldTicket);
		EasyMock.expect(entityManager.merge(newTicket)).andReturn(newTicket);
		EasyMock.replay(entityManager);

		NotificationManager notificationManager = EasyMock.createMock(NotificationManager.class);
		EasyMock.replay(notificationManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);
		projectManager.setNotificationManager(notificationManager);

		User user = new User();
		user.setUsername("TestUser");

		projectManager.saveTicket(newTicket, user);

		EasyMock.verify(entityManager);
		EasyMock.verify(notificationManager);
	}

	public void testSaveTicket_Changes() {
		Long id = new Long(1);

		Ticket oldTicket = new Ticket();
		oldTicket.setId(id);
		oldTicket.setName("Old Ticket Name");

		Ticket newTicket = new Ticket();
		newTicket.setId(id);
		newTicket.setName("New Ticket Name");

		Capture<TicketChange> capturedArgument1 = new Capture<TicketChange>();
		Capture<TicketChange> capturedArgument2 = new Capture<TicketChange>();

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.find(Ticket.class, id)).andReturn(oldTicket);
		EasyMock.expect(entityManager.merge(newTicket)).andReturn(newTicket);
		EasyMock.expect(entityManager.merge(EasyMock.capture(capturedArgument1))).andReturn(null);
		EasyMock.replay(entityManager);

		NotificationManager notificationManager = EasyMock.createMock(NotificationManager.class);
		notificationManager.sendTicketChangeNotification(EasyMock.capture(capturedArgument2));
		EasyMock.replay(notificationManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);
		projectManager.setNotificationManager(notificationManager);

		User user = new User();
		user.setUsername("TestUser");

		projectManager.saveTicket(newTicket, user);

		EasyMock.verify(entityManager);
		EasyMock.verify(notificationManager);

		TicketChange change = capturedArgument1.getValue();
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

		assertSame("change notification", change, capturedArgument2.getValue());
	}

	public void testSaveComment() {
		Comment newComment = new Comment();

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.merge(newComment)).andReturn(newComment);
		EasyMock.replay(entityManager);

		NotificationManager notificationManager = EasyMock.createMock(NotificationManager.class);
		notificationManager.sendCommentNotification(newComment);
		EasyMock.replay(notificationManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);
		projectManager.setNotificationManager(notificationManager);

		projectManager.saveComment(newComment);

		EasyMock.verify(entityManager);
		EasyMock.verify(notificationManager);
	}

	public void testSaveAttachment() {
		Attachment newAttachment = new Attachment();

		EntityManager entityManager = EasyMock.createMock(EntityManager.class);
		EasyMock.expect(entityManager.merge(newAttachment)).andReturn(newAttachment);
		EasyMock.replay(entityManager);

		NotificationManager notificationManager = EasyMock.createMock(NotificationManager.class);
		notificationManager.sendAttachmentNotification(newAttachment);
		EasyMock.replay(notificationManager);

		JpaProjectManager projectManager = new JpaProjectManager();
		projectManager.setEntityManager(entityManager);
		projectManager.setNotificationManager(notificationManager);

		projectManager.saveAttachment(newAttachment);

		EasyMock.verify(entityManager);
		EasyMock.verify(notificationManager);
	}

}
