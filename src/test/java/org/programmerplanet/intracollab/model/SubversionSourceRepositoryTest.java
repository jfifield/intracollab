package org.programmerplanet.intracollab.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class SubversionSourceRepositoryTest extends TestCase {

	public void testGetLastChangePoint_RepositoryChanges() throws Exception {
		List<RepositoryChange> repositoryChanges = new LinkedList<RepositoryChange>();

		Set<RepositoryChangeFile> files1 = new HashSet<RepositoryChangeFile>();
		files1.add(createRepositoryChangeFile("1"));
		files1.add(createRepositoryChangeFile("1"));
		repositoryChanges.add(createRepositoryChange("TestUser", "11/28/2009 10:42:23", "This is a test comment for ticket #1.", files1));

		Set<RepositoryChangeFile> files2 = new HashSet<RepositoryChangeFile>();
		files2.add(createRepositoryChangeFile("2"));
		files2.add(createRepositoryChangeFile("2"));
		repositoryChanges.add(createRepositoryChange("TestUser", "11/28/2009 10:46:40", "This is a test comment for ticket #2.", files2));

		SubversionSourceRepository sourceRepository = new SubversionSourceRepository();
		Long lastChangePoint = sourceRepository.getLastChangePoint(repositoryChanges);
		assertEquals("lastChangePoint", Long.valueOf(2), lastChangePoint);
	}

	private RepositoryChangeFile createRepositoryChangeFile(String revision) {
		RepositoryChangeFile file = new RepositoryChangeFile();
		file.setRevision(revision);
		return file;
	}

	private RepositoryChange createRepositoryChange(String username, String changeDate, String comment, Set<RepositoryChangeFile> files) throws ParseException {
		RepositoryChange change = new RepositoryChange();
		change.setUsername(username);
		change.setChangeDate(date(changeDate));
		change.setComment(comment);
		change.setFiles(files);
		return change;
	}

	private Date date(String str) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse(str);
	}

}
