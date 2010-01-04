package org.programmerplanet.intracollab.web.admin.configuration;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.configuration.ConfigurationManager;
import org.programmerplanet.intracollab.web.spring.SimpleMultiActionFormController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class ConfigurationEditController extends SimpleMultiActionFormController {

	private ConfigurationManager configurationManager;

	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		ConfigurationForm form = (ConfigurationForm)command;
		Map<String, String> configuration = form.getConfiguration();
		configurationManager.save(configuration);
		return new ModelAndView(this.getSuccessView());
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		return new ModelAndView(this.getSuccessView());
	}

	/**
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#suppressBinding(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean suppressBinding(HttpServletRequest request) {
		return !WebUtils.hasSubmitParameter(request, "__save");
	}

	/**
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		ConfigurationForm form = new ConfigurationForm();
		Map<String, String> configuration = configurationManager.get();
		form.setConfiguration(configuration);
		return form;
	}

}
