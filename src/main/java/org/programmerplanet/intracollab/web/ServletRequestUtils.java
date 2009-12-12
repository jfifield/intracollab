package org.programmerplanet.intracollab.web;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Common utility functions dealing with a <code>ServletRequest</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public final class ServletRequestUtils {

	/**
	 * Returns a servlet request parameter as a <code>Long</code>, or
	 * <code>null</code> if the parameter is not present or an empty string.
	 * 
	 * @throws InvalidRequestParameterException if the request parameter cannot be parsed as a <code>Long</code>.
	 */
	public static Long getLongParameter(ServletRequest request, String name) {
		String stringValue = request.getParameter(name);
		Long longValue = null;
		if (StringUtils.isNotEmpty(stringValue)) {
			longValue = getRequiredLongParameter(request, name);
		}
		return longValue;
	}

	/**
	 * Returns a servlet request parameter as a <code>Long</code>.
	 * 
	 * @throws InvalidRequestParameterException if the request parameter cannot be parsed as a <code>Long</code> or is not present or an empty string.
	 */
	public static Long getRequiredLongParameter(ServletRequest request, String name) {
		String stringValue = request.getParameter(name);
		Long longValue = null;
		try {
			longValue = Long.valueOf(stringValue);
		} catch (NumberFormatException e) {
			// ignore
		}
		if (longValue == null) {
			throw new InvalidRequestParameterException(name, stringValue);
		}
		return longValue;
	}

}
