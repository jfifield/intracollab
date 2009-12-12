package org.programmerplanet.intracollab.web.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.UserManager;
import org.programmerplanet.intracollab.model.User;
import org.programmerplanet.intracollab.web.ServletRequestUtils;
import org.programmerplanet.intracollab.web.spring.SimpleMultiActionFormController;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserEditController extends SimpleMultiActionFormController {

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserForm userForm = (UserForm)command;
		User user = null;
		if (userForm.getId() != null) {
			user = userManager.getUser(userForm.getId());
		} else {
			user = new User();
		}
		BeanUtils.copyProperties(userForm, user);
		String password = userForm.getPassword1();
		password = User.getPasswordHash(password);
		user.setPassword(password);
		userManager.saveUser(user);
		return new ModelAndView(this.getSuccessView());
	}

	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserForm userForm = (UserForm)command;
		User user = userManager.getUser(userForm.getId());
		userManager.deleteUser(user);
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
		UserForm userForm = new UserForm();
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		if (id != null) {
			User user = userManager.getUser(id);
			BeanUtils.copyProperties(user, userForm);
		}
		return userForm;
	}

}
