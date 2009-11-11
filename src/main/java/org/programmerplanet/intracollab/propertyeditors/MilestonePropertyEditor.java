package org.programmerplanet.intracollab.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Milestone;

/**
 * Property editor for <code>org.programmerplanet.intracollab.model.Milestone</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class MilestonePropertyEditor extends PropertyEditorSupport {

	private ProjectManager projectManager;

	public MilestonePropertyEditor(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see java.beans.PropertyEditorSupport#getAsText()
	 */
	public String getAsText() {
		Milestone milestone = (Milestone)getValue();
		if (milestone != null) {
			return milestone.getId().toString();
		} else {
			return null;
		}
	}

	/**
	 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		Milestone milestone = null;
		if (StringUtils.isNotEmpty(text)) {
			Long id = Long.valueOf(text);
			milestone = projectManager.getMilestone(id);
		}
		setValue(milestone);
	}

}