package org.programmerplanet.intracollab.web.displaytag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.displaytag.util.RequestHelper;
import org.displaytag.util.RequestHelperFactory;

/**
 * Custom <code>RequestHelperFactory</code> that simply returns an instance of
 * a custom <code>RequestHelper</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class DisplayTagRequestHelperFactory implements RequestHelperFactory {

	/**
	 * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
	 */
	public RequestHelper getRequestHelperInstance(PageContext pageContext) {
		return new DisplayTagRequestHelper((HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse());
	}

}
