package org.programmerplanet.intracollab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.programmerplanet.intracollab.model.User;

/**
 * Convenience class to access common objects in the user's session.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserSession {

	private static final String USER_SESSION_KEY = UserSession.class.getName();

	private User user;

	public static void initialize(HttpServletRequest request, User user) {
		UserSession userSession = new UserSession(user);
		request.getSession().setAttribute(USER_SESSION_KEY, userSession);
	}

	public static void destroy(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(USER_SESSION_KEY);
			session.invalidate();
		}
	}

	public static UserSession getUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		UserSession userSession = null;
		if (session != null) {
			userSession = (UserSession)session.getAttribute(USER_SESSION_KEY);
		}
		return userSession;
	}

	public UserSession(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
