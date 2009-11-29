package org.programmerplanet.intracollab.web.taglib;

import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Custom tag used to translate custom markup in the body. For example, a #
 * followed by one or more digits is a reference to a ticket. A link is added
 * to navigate to the ticket.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class MarkupTag extends BodyTagSupport {

	/**
	 * Ticket reference search pattern.
	 */
	private static final Pattern TICKET_SEARCH = Pattern.compile("#(\\d+)");

	/**
	 * Ticket reference replace pattern.
	 */
	private static final String TICKET_REPLACE = "<a href=\"ticket/view.do?id=$1\">#$1</a>";

	/**
	 * Repository change reference search pattern.
	 */
	private static final Pattern REPOSITORY_CHANGE_SEARCH = Pattern.compile("\\[(\\d+)\\]");

	/**
	 * Repository change reference replace pattern.
	 */
	private static final String REPOSITORY_CHANGE_REPLACE = "<a href=\"repository_change/view.do?id=$1\">[$1]</a>";

	/**
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		String content = bodyContent.getString();
		content = TICKET_SEARCH.matcher(content).replaceAll(TICKET_REPLACE);
		content = REPOSITORY_CHANGE_SEARCH.matcher(content).replaceAll(REPOSITORY_CHANGE_REPLACE);
		try {
			pageContext.getOut().print(content);
		} catch (Exception e) {
			throw new JspTagException(e);
		}
		return EVAL_PAGE;
	}

}
