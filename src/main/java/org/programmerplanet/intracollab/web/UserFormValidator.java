package org.programmerplanet.intracollab.web;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.manager.UserManager;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2007 Joseph Fifield
 */
public class UserFormValidator implements Validator {

	private static final int MINIMUM_PASSWORD_LENGTH = 6;
	private static final Pattern USERNAME_PATTERN = Pattern.compile("\\w+");

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(UserForm.class);
	}

	public void validate(Object obj, Errors errors) {
		UserForm user = (UserForm)obj;

		// username is required
		if (StringUtils.isEmpty(user.getUsername())) {
			errors.rejectValue("username", "error.required");
		}
		// username must be regular non-whitespace characters
		else if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()) {
			errors.rejectValue("username", "error.user.username.invalid");
		}
		// username must be unique
		else if (!userManager.isUsernameUnique(user.getId(), user.getUsername())) {
			errors.rejectValue("username", "error.user.username.exists");
		}

		// password is required
		if (StringUtils.isEmpty(user.getPassword1())) {
			if (user.getId() == null) {
				errors.rejectValue("password1", "error.required");
			}
		}
		// password must be at least 6 characters
		else if (user.getPassword1().length() < MINIMUM_PASSWORD_LENGTH) {
			errors.rejectValue("password1", "error.user.password.length", new Object[] { Integer.valueOf(MINIMUM_PASSWORD_LENGTH) }, null);
		}
		// passwords must match
		else if (!user.getPassword1().equals(user.getPassword2())) {
			errors.rejectValue("password2", "error.user.password.nomatch");
		}

		// email address is required
		if (StringUtils.isEmpty(user.getEmailAddress())) {
			errors.rejectValue("emailAddress", "error.required");
		}
	}
}
