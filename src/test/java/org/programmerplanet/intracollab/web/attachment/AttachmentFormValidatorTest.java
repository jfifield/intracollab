package org.programmerplanet.intracollab.web.attachment;

import org.easymock.EasyMock;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class AttachmentFormValidatorTest extends TestCase {

	public void testValidate_Good() {
		AttachmentFormValidator validator = new AttachmentFormValidator();

		MultipartFile multipartFile = EasyMock.createMock(MultipartFile.class);
		EasyMock.expect(multipartFile.getOriginalFilename()).andReturn("TestFile.txt");
		EasyMock.replay(multipartFile);

		AttachmentForm form = new AttachmentForm();
		form.setFile(multipartFile);

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingFile() {
		AttachmentFormValidator validator = new AttachmentFormValidator();

		MultipartFile multipartFile = EasyMock.createMock(MultipartFile.class);
		EasyMock.expect(multipartFile.getOriginalFilename()).andReturn(null);
		EasyMock.replay(multipartFile);

		AttachmentForm form = new AttachmentForm();
		form.setFile(multipartFile);

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("file"));
	}

}
