package org.programmerplanet.intracollab.web;

import javax.servlet.ServletRequest;

import org.easymock.EasyMock;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ServletRequestUtilsTest extends TestCase {

	public void testGetLongParameter_Null() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", null);
		Long parameter = ServletRequestUtils.getLongParameter(servletRequest, "test");
		assertNull("parameter", parameter);
	}

	public void testGetLongParameter_EmptyString() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", "");
		Long parameter = ServletRequestUtils.getLongParameter(servletRequest, "test");
		assertNull("parameter", parameter);
	}

	public void testGetLongParameter_Invalid() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", "ABC");
		try {
			ServletRequestUtils.getLongParameter(servletRequest, "test");
			fail("Expected InvalidRequestParameterException");
		} catch (InvalidRequestParameterException e) {
			// expected
		}
	}

	public void testGetLongParameter_Valid() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", "1");
		Long parameter = ServletRequestUtils.getLongParameter(servletRequest, "test");
		assertEquals("parameter", Long.valueOf(1), parameter);
	}

	public void testGetRequiredLongParameter_Null() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", null);
		try {
			ServletRequestUtils.getRequiredLongParameter(servletRequest, "test");
			fail("Expected InvalidRequestParameterException");
		} catch (InvalidRequestParameterException e) {
			// expected
		}
	}

	public void testGetRequiredLongParameter_EmptyString() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", "");
		try {
			ServletRequestUtils.getRequiredLongParameter(servletRequest, "test");
			fail("Expected InvalidRequestParameterException");
		} catch (InvalidRequestParameterException e) {
			// expected
		}
	}

	public void testGetRequiredLongParameter_Invalid() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", "ABC");
		try {
			ServletRequestUtils.getRequiredLongParameter(servletRequest, "test");
			fail("Expected InvalidRequestParameterException");
		} catch (InvalidRequestParameterException e) {
			// expected
		}
	}

	public void testGetRequiredLongParameter_Valid() {
		ServletRequest servletRequest = getTestServletRequestWithParameter("test", "1");
		Long parameter = ServletRequestUtils.getRequiredLongParameter(servletRequest, "test");
		assertEquals("parameter", Long.valueOf(1), parameter);
	}

	private ServletRequest getTestServletRequestWithParameter(String name, String value) {
		ServletRequest servletRequest = EasyMock.createMock(ServletRequest.class);
		EasyMock.expect(servletRequest.getParameter(name)).andReturn(value).anyTimes();
		EasyMock.replay(servletRequest);
		return servletRequest;
	}

}
