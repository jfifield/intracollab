package org.programmerplanet.intracollab.web.admin.configuration;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class ConfigurationFormValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(ConfigurationForm.class);
	}

	public void validate(Object obj, Errors errors) {
		ConfigurationForm configuration = (ConfigurationForm)obj;
		if (configuration.isEmailNotifications()) {
			// smtp host is required
			if (StringUtils.isEmpty(configuration.getSmtpHost())) {
				errors.rejectValue("smtpHost", "error.required");
			}
			// email sender is required
			if (StringUtils.isEmpty(configuration.getEmailSender())) {
				errors.rejectValue("emailSender", "error.required");
			}
		}
	}

}
