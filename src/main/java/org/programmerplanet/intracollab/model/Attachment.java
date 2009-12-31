package org.programmerplanet.intracollab.model;

import java.util.Date;

/**
 * Represents an attachment within a ticket. This is a heavy-weight representation;
 * it contains the actual attachment contents. See <code>AttachmentInfo</code>
 * for a light-weight representation.
 * 
 * @see AttachmentInfo
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class Attachment extends BaseEntity {

	private Ticket ticket;
	private String fileName;
	private String description;
	private long fileSize;
	private String contentType;
	private byte[] content;
	private Date created;
	private String createdBy;

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

	public void setContent(byte[] content) {
		this.content = content;
	}

	public byte[] getContent() {
		return content;
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
