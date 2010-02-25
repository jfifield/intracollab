package org.programmerplanet.intracollab.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class CvsSourceRepositoryTest extends TestCase {

	public void testGetLastChangePoint_RepositoryChanges() throws Exception {
		List<RepositoryChange> repositoryChanges = new LinkedList<RepositoryChange>();
		repositoryChanges.add(createRepositoryChange("TestUser", "11/28/2009 10:42:23", "This is a test comment for ticket #1."));
		repositoryChanges.add(createRepositoryChange("TestUser", "11/28/2009 10:46:40", "This is a test comment for ticket #2."));

		CvsSourceRepository sourceRepository = new CvsSourceRepository();
		Long lastChangePoint = sourceRepository.getLastChangePoint(repositoryChanges);
		assertEquals("lastChangePoint", Long.valueOf(date("11/28/2009 10:46:40").getTime()), lastChangePoint);
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

}
