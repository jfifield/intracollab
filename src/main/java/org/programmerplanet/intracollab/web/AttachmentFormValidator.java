package org.programmerplanet.intracollab.web;

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
public class AttachmentFormValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(AttachmentForm.class);
	}

	public void validate(Object obj, Errors errors) {
		AttachmentForm attachmentForm = (AttachmentForm)obj;

		// file is required
		if (StringUtils.isEmpty(attachmentForm.getFile().getOriginalFilename())) {
			errors.rejectValue("file", "error.required");
		}
	}

}
