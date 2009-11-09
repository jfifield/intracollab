package org.programmerplanet.intracollab.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.manager.UserManager;
import org.programmerplanet.intracollab.model.User;

/**
 * Property editor for <code>org.programmerplanet.intracollab.model.User</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserPropertyEditor extends PropertyEditorSupport {

	private UserManager userManager;

	public UserPropertyEditor(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	public String getAsText() {
		User user = (User)getValue();
		if (user != null) {
			return user.getId().toString();
		} else {
			return null;
		}
	}

	/**
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		User user = null;
		if (StringUtils.isNotEmpty(text)) {
			Long id = Long.valueOf(text);
			user = userManager.getUser(id);
		}
		setValue(user);
	}

}