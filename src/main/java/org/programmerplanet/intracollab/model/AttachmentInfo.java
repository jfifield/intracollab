package org.programmerplanet.intracollab.model;

import java.util.Date;

/**
 * Represents an attachment within a ticket. This is a light-weight representation;
 * it does not contain the actual attachment contents. See <code>Attachment</code>
 * for a heavy-weight representation.
 * 
 * @see Attachment
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AttachmentInfo {

	private Long id;
	private Ticket ticket;
	private String fileName;
	private String description;
	private long fileSize;
	private String contentType;
	private Date created;
	private String createdBy;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setContentType(String mimeType) {
		this.contentType = mimeType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

}
