package org.programmerplanet.intracollab.model;

/**
 * Exception to indicate that an error that occurred trying to access a source
 * repository.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SourceRepositoryAccessException extends RuntimeException {

	public SourceRepositoryAccessException(Throwable cause) {
		super(cause);
	}

}
