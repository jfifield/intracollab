package org.programmerplanet.intracollab.model;

import java.util.Collection;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class RepositoryChangeTest extends TestCase {

	public void testFindTicketNumbersInComment_Null() {
		RepositoryChange change = new RepositoryChange();
		change.setComment(null);
		Collection<Long> ticketNumbers = change.findTicketNumbersInComment();
		assertNotNull("result is null", ticketNumbers);
		assertEquals("# ticket numbers", 0, ticketNumbers.size());
	}

	public void testFindTicketNumbersInComment_Empty() {
		RepositoryChange change = new RepositoryChange();
		change.setComment("");
		Collection<Long> ticketNumbers = change.findTicketNumbersInComment();
		assertNotNull("result is null", ticketNumbers);
		assertEquals("# ticket numbers", 0, ticketNumbers.size());
	}

	public void testFindTicketNumbersInComment_None() {
		RepositoryChange change = new RepositoryChange();
		change.setComment("This is a test comment with no ticket numbers.");
		Collection<Long> ticketNumbers = change.findTicketNumbersInComment();
		assertNotNull("result is null", ticketNumbers);
		assertEquals("# ticket numbers", 0, ticketNumbers.size());
	}

	public void testFindTicketNumbersInComment() {
		RepositoryChange change = new RepositoryChange();
		change.setComment("This is a test comment with references to ticket #1, #23 and #456.");
		Collection<Long> ticketNumbers = change.findTicketNumbersInComment();
		assertNotNull("result is null", ticketNumbers);
		assertEquals("# ticket numbers", 3, ticketNumbers.size());
		assertTrue("ticket #1", ticketNumbers.contains(Long.valueOf(1)));
		assertTrue("ticket #23", ticketNumbers.contains(Long.valueOf(23)));
		assertTrue("ticket #456", ticketNumbers.contains(Long.valueOf(456)));
	}

}
