package org.programmerplanet.intracollab.web;

/**
 * Exception thrown when an expected request parameter was not present or
 * invalid.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class InvalidRequestParameterException extends RuntimeException {

	public InvalidRequestParameterException(String name, String actualValue) {
		super("Invalid request parameter '" + name + "', actual value: " + actualValue);
	}

}
