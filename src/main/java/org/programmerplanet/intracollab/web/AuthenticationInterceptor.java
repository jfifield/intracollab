package org.programmerplanet.intracollab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <code>HandlerInterceptor</code> implementation that ensures access only by
 * authenticated users. Returns a login view if the user is not authenticated.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private String loginView;

	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}

	public String getLoginView() {
		return loginView;
	}

	/**
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserSession userSession = UserSession.getUserSession(request);
		if (userSession == null) {
			ModelAndView modelAndView = new ModelAndView(this.getLoginView());
			throw new ModelAndViewDefiningException(modelAndView);
		} else {
			return true;
		}
	}

}
