package org.programmerplanet.intracollab.web.spring;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

/**
 * Extension of <code>SimpleFormController</code> that delegates
 * <code>onSubmit()</code> to another method based on a <code>MethodNameResolver</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SimpleMultiActionFormController extends SimpleFormController {

	private MethodNameResolver methodNameResolver;

	public void setMethodNameResolver(MethodNameResolver methodNameResolver) {
		this.methodNameResolver = methodNameResolver;
	}

	public MethodNameResolver getMethodNameResolver() {
		return methodNameResolver;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	protected final ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		String methodName = methodNameResolver.getHandlerMethodName(request);

		Class[] parameterTypes = new Class[] { HttpServletRequest.class, HttpServletResponse.class, Object.class, BindException.class };
		Method method = this.getClass().getMethod(methodName, parameterTypes);
		Object[] parameters = new Object[] { request, response, command, errors };
		Object object = method.invoke(this, parameters);
		ModelAndView modelAndView = (ModelAndView)object;
		return modelAndView;
	}

}
