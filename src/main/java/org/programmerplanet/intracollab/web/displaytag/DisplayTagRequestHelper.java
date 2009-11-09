package org.programmerplanet.intracollab.web.displaytag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.util.DefaultHref;
import org.displaytag.util.DefaultRequestHelper;
import org.displaytag.util.Href;

/**
 * Custom <code>RequestHelper</code> that returns the URL of the original
 * request, instead of the JSP view being rendered. This makes the table
 * sorting and paging links correct by default.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class DisplayTagRequestHelper extends DefaultRequestHelper {

	private static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";

	private HttpServletRequest request;
	private HttpServletResponse response;

	public DisplayTagRequestHelper(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		this.request = request;
		this.response = response;
	}

	/**
	 * @see org.displaytag.util.DefaultRequestHelper#getHref()
	 */
	public Href getHref() {
		String uri = (String)this.request.getAttribute(FORWARD_REQUEST_URI);
		uri = this.response.encodeURL(uri);
		Href href = new DefaultHref(uri);
		href.setParameterMap(getParameterMap());
		return href;
	}

}
