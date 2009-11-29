package org.programmerplanet.intracollab.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.web.AuthorizationException;
import org.programmerplanet.intracollab.web.UserSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <code>HandlerInterceptor</code> implementation that prevents access if the
 * current user is not an administrator.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AdministrationInterceptor extends HandlerInterceptorAdapter {

	/**
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserSession userSession = UserSession.getUserSession(request);
		if (userSession != null && userSession.getUser().isAdministrator()) {
			return true;
		} else {
			throw new AuthorizationException("User is not an administrator!");
		}
	}

}
