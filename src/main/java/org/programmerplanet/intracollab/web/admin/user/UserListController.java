package org.programmerplanet.intracollab.web.admin.user;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.UserManager;
import org.programmerplanet.intracollab.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserListController implements Controller {

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection<User> users = userManager.getUsers();
		return new ModelAndView("admin/user/list", "users", users);
	}

}
