package org.programmerplanet.intracollab.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class LoginFormValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(LoginForm.class);
	}

	public void validate(Object obj, Errors errors) {
		LoginForm loginForm = (LoginForm)obj;

		// username is required
		if (StringUtils.isEmpty(loginForm.getUsername())) {
			errors.rejectValue("username", "error.required");
		}

		// password is required
		if (StringUtils.isEmpty(loginForm.getPassword())) {
			errors.rejectValue("password", "error.required");
		}
	}

}
