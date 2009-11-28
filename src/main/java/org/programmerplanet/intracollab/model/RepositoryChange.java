package org.programmerplanet.intracollab.model;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a change to one or more files in the source repository.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class RepositoryChange {

	/**
	 * Regular expression pattern for matching ticket number references in the comments. 
	 */
	private static final Pattern TICKET_NUMBER_PATTERN = Pattern.compile("#(\\d+)");

	private Long id;
	private Project project;
	private String username;
	private Date changeDate;
	private String comment;
	private Set<RepositoryChangeFile> files;
	private Set<Ticket> tickets;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
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

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setFiles(Set<RepositoryChangeFile> files) {
		this.files = files;
	}

	public Set<RepositoryChangeFile> getFiles() {
		return files;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * Parses and returns a collection of ticket numbers referenced in the
	 * comment. Each occurrence of a # followed by one or more digits is
	 * considered a reference to a ticket.
	 */
	public Collection<Long> findTicketNumbersInComment() {
		Collection<Long> ticketNumbers = new LinkedList<Long>();
		if (comment != null) {
			Matcher matcher = TICKET_NUMBER_PATTERN.matcher(comment);
			while (matcher.find()) {
				String match = matcher.group(1);
				Long ticketNumber = Long.valueOf(match);
				ticketNumbers.add(ticketNumber);
			}
		}
		return ticketNumbers;
	}

}
