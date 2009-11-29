package org.programmerplanet.intracollab.web.admin.user;

import org.easymock.EasyMock;
import org.programmerplanet.intracollab.manager.UserManager;
import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserFormValidatorTest extends TestCase {

	public void testValidate_Good() {
		UserFormValidator validator = new UserFormValidator();

		UserManager userManager = EasyMock.createMock(UserManager.class);
		EasyMock.expect(userManager.isUsernameUnique(null, "TestUser")).andReturn(Boolean.TRUE);
		EasyMock.replay(userManager);
		validator.setUserManager(userManager);

		UserForm form = new UserForm();
		form.setUsername("TestUser");
		form.setPassword1("TestPassword");
		form.setPassword2("TestPassword");
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingUsername() {
		UserFormValidator validator = new UserFormValidator();

		UserForm form = new UserForm();
		form.setUsername(null);
		form.setPassword1("TestPassword");
		form.setPassword2("TestPassword");
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("username"));
	}

	public void testValidate_InvalidUsername() {
		UserFormValidator validator = new UserFormValidator();

		UserForm form = new UserForm();
		form.setUsername("Test User");
		form.setPassword1("TestPassword");
		form.setPassword2("TestPassword");
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("username"));
	}

	public void testValidate_DuplicateUsername() {
		UserFormValidator validator = new UserFormValidator();

		UserManager userManager = EasyMock.createMock(UserManager.class);
		EasyMock.expect(userManager.isUsernameUnique(null, "TestUser")).andReturn(Boolean.FALSE);
		EasyMock.replay(userManager);
		validator.setUserManager(userManager);

		UserForm form = new UserForm();
		form.setUsername("TestUser");
		form.setPassword1("TestPassword");
		form.setPassword2("TestPassword");
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("username"));
	}

	public void testValidate_MissingPassword() {
		UserFormValidator validator = new UserFormValidator();

		UserManager userManager = EasyMock.createMock(UserManager.class);
		EasyMock.expect(userManager.isUsernameUnique(null, "TestUser")).andReturn(Boolean.TRUE);
		EasyMock.replay(userManager);
		validator.setUserManager(userManager);

		UserForm form = new UserForm();
		form.setUsername("TestUser");
		form.setPassword1(null);
		form.setPassword2(null);
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("password1"));
	}

	public void testValidate_PasswordTooShort() {
		UserFormValidator validator = new UserFormValidator();

		UserManager userManager = EasyMock.createMock(UserManager.class);
		EasyMock.expect(userManager.isUsernameUnique(null, "TestUser")).andReturn(Boolean.TRUE);
		EasyMock.replay(userManager);
		validator.setUserManager(userManager);

		UserForm form = new UserForm();
		form.setUsername("TestUser");
		form.setPassword1("12345");
		form.setPassword2("12345");
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("password1"));
	}

	public void testValidate_PasswordsDoNotMatch() {
		UserFormValidator validator = new UserFormValidator();

		UserManager userManager = EasyMock.createMock(UserManager.class);
		EasyMock.expect(userManager.isUsernameUnique(null, "TestUser")).andReturn(Boolean.TRUE);
		EasyMock.replay(userManager);
		validator.setUserManager(userManager);

		UserForm form = new UserForm();
		form.setUsername("TestUser");
		form.setPassword1("TestPassword");
		form.setPassword2("TestPassword2");
		form.setEmailAddress("TestUser@TestDomain.com");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("password2"));
	}

	public void testValidate_MissingEmailAddress() {
		UserFormValidator validator = new UserFormValidator();

		UserManager userManager = EasyMock.createMock(UserManager.class);
		EasyMock.expect(userManager.isUsernameUnique(null, "TestUser")).andReturn(Boolean.TRUE);
		EasyMock.replay(userManager);
		validator.setUserManager(userManager);

		UserForm form = new UserForm();
		form.setUsername("TestUser");
		form.setPassword1("TestPassword");
		form.setPassword2("TestPassword");
		form.setEmailAddress("");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("emailAddress"));
	}

}
