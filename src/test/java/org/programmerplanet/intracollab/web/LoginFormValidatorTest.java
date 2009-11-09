package org.programmerplanet.intracollab.web;

import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class LoginFormValidatorTest extends TestCase {

	public void testValidate_Good() {
		LoginFormValidator validator = new LoginFormValidator();

		LoginForm form = new LoginForm();
		form.setUsername("TestUser");
		form.setPassword("TestPassword");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingUsername() {
		LoginFormValidator validator = new LoginFormValidator();

		LoginForm form = new LoginForm();
		form.setUsername("");
		form.setPassword("TestPassword");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("username"));
	}

	public void testValidate_MissingPassword() {
		LoginFormValidator validator = new LoginFormValidator();

		LoginForm form = new LoginForm();
		form.setUsername("TestUser");
		form.setPassword("");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("password"));
	}

}
