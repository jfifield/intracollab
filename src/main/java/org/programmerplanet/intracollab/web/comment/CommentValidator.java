package org.programmerplanet.intracollab.web.comment;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.model.Comment;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class CommentValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(Comment.class);
	}

	public void validate(Object obj, Errors errors) {
		Comment comment = (Comment)obj;

		// content is required
		if (StringUtils.isEmpty(comment.getContent())) {
			errors.rejectValue("content", "error.required");
		}
	}

}
