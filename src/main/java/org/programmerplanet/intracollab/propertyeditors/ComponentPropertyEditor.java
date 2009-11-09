package org.programmerplanet.intracollab.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Component;

/**
 * Property editor for <code>org.programmerplanet.intracollab.model.Component</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ComponentPropertyEditor extends PropertyEditorSupport {

	private ProjectManager projectManager;

	public ComponentPropertyEditor(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	public String getAsText() {
		Component component = (Component)getValue();
		if (component != null) {
			return component.getId().toString();
		} else {
			return null;
		}
	}

	/**
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		Component component = null;
		if (StringUtils.isNotEmpty(text)) {
			Long id = Long.valueOf(text);
			component = projectManager.getComponent(id);
		}
		setValue(component);
	}

}