package org.programmerplanet.intracollab.web.admin.configuration;

import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class ConfigurationFormValidatorTest extends TestCase {

	public void testValidate_EmailNotificationsOff() {
		ConfigurationFormValidator validator = new ConfigurationFormValidator();

		ConfigurationForm form = new ConfigurationForm();
		form.setEmailNotifications(false);
		form.setSmtpHost(null);
		form.setEmailSender(null);

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_Good() {
		ConfigurationFormValidator validator = new ConfigurationFormValidator();

		ConfigurationForm form = new ConfigurationForm();
		form.setEmailNotifications(true);
		form.setSmtpHost("localhost");
		form.setEmailSender("root@localhost");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingSmtpHost() {
		ConfigurationFormValidator validator = new ConfigurationFormValidator();

		ConfigurationForm form = new ConfigurationForm();
		form.setEmailNotifications(true);
		form.setSmtpHost(null);
		form.setEmailSender("root@localhost");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("smtpHost"));
	}

	public void testValidate_MissingEmailSender() {
		ConfigurationFormValidator validator = new ConfigurationFormValidator();

		ConfigurationForm form = new ConfigurationForm();
		form.setEmailNotifications(true);
		form.setSmtpHost("localhost");
		form.setEmailSender(null);

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("emailSender"));
	}

}
