package org.programmerplanet.intracollab.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.programmerplanet.intracollab.model.RepositoryChange;
import org.programmerplanet.intracollab.model.SourceRepository;
import org.programmerplanet.intracollab.model.SourceRepositoryAccessException;
import org.programmerplanet.intracollab.model.Ticket;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class RepositoryChangeScanTaskTest extends TestCase {

	public void testRun_NoSourceRepositories() {
		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.getSourceRepositories()).andReturn(Collections.EMPTY_SET);
		EasyMock.replay(projectManager);

		RepositoryChangeScanTask task = new RepositoryChangeScanTask();
		task.setProjectManager(projectManager);

		task.run();
	}

	public void testRun_NoChanges() {
		SourceRepository sourceRepository = new TestSourceRepository(Collections.EMPTY_LIST);

		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.getSourceRepositories()).andReturn(Collections.singleton(sourceRepository));
		EasyMock.replay(projectManager);

		RepositoryChangeScanTask task = new RepositoryChangeScanTask();
		task.setProjectManager(projectManager);

		task.run();
	}

	public void testRun_Changes() throws Exception {
		List<RepositoryChange> repositoryChanges = new LinkedList<RepositoryChange>();
		RepositoryChange change1 = createRepositoryChange("TestUser", "11/28/2009 10:42:23", "This is a test comment for ticket #1.");
		RepositoryChange change2 = createRepositoryChange("TestUser", "11/28/2009 10:46:40", "This is a test comment for ticket #2.");
		repositoryChanges.add(change1);
		repositoryChanges.add(change2);

		SourceRepository sourceRepository = new TestSourceRepository(repositoryChanges);

		Ticket ticket1 = new Ticket();
		Ticket ticket2 = new Ticket();

		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.getSourceRepositories()).andReturn(Collections.singleton(sourceRepository));
		EasyMock.expect(projectManager.getTicket(Long.valueOf(1))).andReturn(ticket1);
		EasyMock.expect(projectManager.getTicket(Long.valueOf(2))).andReturn(ticket2);
		projectManager.saveRepositoryChanges(sourceRepository, repositoryChanges);
		EasyMock.replay(projectManager);

		RepositoryChangeScanTask task = new RepositoryChangeScanTask();
		task.setProjectManager(projectManager);

		task.run();

		// verify that the tickets are linked
		assertEquals("# tickets in change1", 1, change1.getTickets().size());
		assertTrue("ticket1 not linked to change1", change1.getTickets().contains(ticket1));
		assertEquals("# tickets in change2", 1, change2.getTickets().size());
		assertTrue("ticket2 not linked to change2", change2.getTickets().contains(ticket2));

		// verify the last change date
		assertEquals("sourceRepository.lastChangeDate", date("11/28/2009 10:46:40"), sourceRepository.getLastChangeDate());
	}

	public void testRun_Changes_UnknownTickets() throws Exception {
		List<RepositoryChange> repositoryChanges = new LinkedList<RepositoryChange>();
		RepositoryChange change1 = createRepositoryChange("TestUser", "11/28/2009 10:42:23", "This is a test comment for ticket #1.");
		RepositoryChange change2 = createRepositoryChange("TestUser", "11/28/2009 10:46:40", "This is a test comment for ticket #2.");
		repositoryChanges.add(change1);
		repositoryChanges.add(change2);

		SourceRepository sourceRepository = new TestSourceRepository(repositoryChanges);

		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.getSourceRepositories()).andReturn(Collections.singleton(sourceRepository));
		EasyMock.expect(projectManager.getTicket(Long.valueOf(1))).andReturn(null);
		EasyMock.expect(projectManager.getTicket(Long.valueOf(2))).andReturn(null);
		projectManager.saveRepositoryChanges(sourceRepository, repositoryChanges);
		EasyMock.replay(projectManager);

		RepositoryChangeScanTask task = new RepositoryChangeScanTask();
		task.setProjectManager(projectManager);

		task.run();

		// verify that no tickets are linked
		assertEquals("# tickets in change1", 0, change1.getTickets().size());
		assertEquals("# tickets in change2", 0, change2.getTickets().size());

		// verify the last change date
		assertEquals("sourceRepository.lastChangeDate", date("11/28/2009 10:46:40"), sourceRepository.getLastChangeDate());
	}

	public void testRun_SourceRepositoryAccessException() throws Exception {
		List<RepositoryChange> repositoryChanges = new LinkedList<RepositoryChange>();
		RepositoryChange change1 = createRepositoryChange("TestUser", "11/28/2009 10:42:23", "This is a test comment for ticket #1.");
		RepositoryChange change2 = createRepositoryChange("TestUser", "11/28/2009 10:46:40", "This is a test comment for ticket #2.");
		repositoryChanges.add(change1);
		repositoryChanges.add(change2);

		List<SourceRepository> sourceRepositories = new LinkedList<SourceRepository>();
		SourceRepository sourceRepository1 = new TestSourceRepository(new SourceRepositoryAccessException(null));
		SourceRepository sourceRepository2 = new TestSourceRepository(repositoryChanges);
		sourceRepositories.add(sourceRepository1);
		sourceRepositories.add(sourceRepository2);

		Ticket ticket1 = new Ticket();
		Ticket ticket2 = new Ticket();

		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.getSourceRepositories()).andReturn(sourceRepositories);
		EasyMock.expect(projectManager.getTicket(Long.valueOf(1))).andReturn(ticket1);
		EasyMock.expect(projectManager.getTicket(Long.valueOf(2))).andReturn(ticket2);
		projectManager.saveRepositoryChanges(sourceRepository2, repositoryChanges);
		EasyMock.replay(projectManager);

		RepositoryChangeScanTask task = new RepositoryChangeScanTask();
		task.setProjectManager(projectManager);

		task.run();

		// verify that the tickets are linked
		assertEquals("# tickets in change1", 1, change1.getTickets().size());
		assertTrue("ticket1 not linked to change1", change1.getTickets().contains(ticket1));
		assertEquals("# tickets in change2", 1, change2.getTickets().size());
		assertTrue("ticket2 not linked to change2", change2.getTickets().contains(ticket2));

		// verify the last change date
		assertEquals("sourceRepository.lastChangeDate", date("11/28/2009 10:46:40"), sourceRepository2.getLastChangeDate());
	}

	private RepositoryChange createRepositoryChange(String username, String changeDate, String comment) throws ParseException {
		RepositoryChange change = new RepositoryChange();
		change.setUsername(username);
		change.setChangeDate(date(changeDate));
		change.setComment(comment);
		return change;
	}

	private Date date(String str) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse(str);
	}

	private static class TestSourceRepository extends SourceRepository {

		private RuntimeException exception;
		private List<RepositoryChange> repositoryChanges;

		public TestSourceRepository(RuntimeException exception) {
			this.exception = exception;
		}

		public TestSourceRepository(List<RepositoryChange> repositoryChanges) {
			this.repositoryChanges = repositoryChanges;
		}

		public List<RepositoryChange> getRepositoryChanges() {
			if (exception != null) {
				throw exception;
			} else {
				return repositoryChanges;
			}
		}

	}

}
