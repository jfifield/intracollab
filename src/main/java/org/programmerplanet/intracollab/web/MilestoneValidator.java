package org.programmerplanet.intracollab.web;

import org.apache.commons.lang.StringUtils;
import org.programmerplanet.intracollab.model.Milestone;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class MilestoneValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(Milestone.class);
	}

	public void validate(Object obj, Errors errors) {
		Milestone milestone = (Milestone)obj;

		// name is required
		if (StringUtils.isEmpty(milestone.getName())) {
			errors.rejectValue("name", "error.required");
		}

		// due date is required
		if (milestone.getDueDate() == null) {
			errors.rejectValue("dueDate", "error.required");
		}
	}

}
