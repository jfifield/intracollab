package org.programmerplanet.intracollab.web.comment;

import org.programmerplanet.intracollab.model.Comment;
import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class CommentValidatorTest extends TestCase {

	public void testValidate_Good() {
		CommentValidator validator = new CommentValidator();

		Comment comment = new Comment();
		comment.setContent("Test Content");

		BindException errors = new BindException(comment, "comment");

		validator.validate(comment, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingContent() {
		CommentValidator validator = new CommentValidator();

		Comment comment = new Comment();
		comment.setContent(null);

		BindException errors = new BindException(comment, "comment");

		validator.validate(comment, errors);

		assertTrue("errors", errors.hasFieldErrors("content"));
	}

}
