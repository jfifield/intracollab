package org.programmerplanet.intracollab.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * Custom tag used to highlight specified text in the body.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class HighlightTag extends BodyTagSupport {

	private static final String HIGHLIGHT_BEGIN = "<span class=\"highlight\">";
	private static final String HIGHLIGHT_END = "</span>";

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	/**
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		String content = bodyContent.getString();
		content = StringUtils.replace(content, text, HIGHLIGHT_BEGIN + text + HIGHLIGHT_END);
		try {
			pageContext.getOut().print(content);
		} catch (Exception e) {
			throw new JspTagException(e);
		}
		return EVAL_PAGE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
	 */
	public void release() {
		this.text = null;
	}

}
