package org.programmerplanet.intracollab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.UserManager;
import org.programmerplanet.intracollab.model.User;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class LoginController extends SimpleFormController {

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		LoginForm loginForm = (LoginForm)command;

		User user = userManager.getUser(loginForm.getUsername(), loginForm.getPassword());

		if (user == null) {
			// login bad!
			errors.reject("error.loginfailed");
			return this.showForm(request, response, errors);
		} else {
			// login good!
			UserSession.initialize(request, user);
			return new ModelAndView(getSuccessView());
		}
	}

}
