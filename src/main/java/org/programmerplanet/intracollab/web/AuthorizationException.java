package org.programmerplanet.intracollab.web;

/**
 * Exception thrown when the user attempts to access a resource without the
 * proper authorization.
 * 
 * @author <a href="mailto:jfifield@programmerplanet.org">Joseph Fifield<a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AuthorizationException extends Exception {

	public AuthorizationException(String message) {
		super(message);
	}

}
