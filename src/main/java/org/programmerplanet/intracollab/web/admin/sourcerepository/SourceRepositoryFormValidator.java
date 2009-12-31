package org.programmerplanet.intracollab.web.admin.sourcerepository;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SourceRepositoryFormValidator implements Validator {

	private static final String TYPE_NONE = "none";

	public boolean supports(Class clazz) {
		return clazz.equals(SourceRepositoryForm.class);
	}

	public void validate(Object obj, Errors errors) {
		SourceRepositoryForm form = (SourceRepositoryForm)obj;

		// type is required
		if (StringUtils.isEmpty(form.getType())) {
			errors.rejectValue("type", "error.required");
		}

		if (!TYPE_NONE.equals(form.getType())) {
			// path is required
			if (StringUtils.isEmpty(form.getPath())) {
				errors.rejectValue("path", "error.required");
			}

			// modules is required
			if (StringUtils.isEmpty(form.getModules())) {
				errors.rejectValue("modules", "error.required");
			}
		}
	}

}
