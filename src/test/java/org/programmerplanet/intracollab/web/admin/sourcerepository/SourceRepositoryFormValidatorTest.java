package org.programmerplanet.intracollab.web.admin.sourcerepository;

import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SourceRepositoryFormValidatorTest extends TestCase {

	public void testValidate_TypeNone() {
		SourceRepositoryFormValidator validator = new SourceRepositoryFormValidator();

		SourceRepositoryForm form = new SourceRepositoryForm();
		form.setType("none");
		form.setPath(null);
		form.setModules(null);

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_Good() {
		SourceRepositoryFormValidator validator = new SourceRepositoryFormValidator();

		SourceRepositoryForm form = new SourceRepositoryForm();
		form.setType("cvs");
		form.setPath("/cvsroot");
		form.setModules("TestProject");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingPath() {
		SourceRepositoryFormValidator validator = new SourceRepositoryFormValidator();

		SourceRepositoryForm form = new SourceRepositoryForm();
		form.setType("cvs");
		form.setPath(null);
		form.setModules("TestProject");

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("path"));
	}

	public void testValidate_MissingModules() {
		SourceRepositoryFormValidator validator = new SourceRepositoryFormValidator();

		SourceRepositoryForm form = new SourceRepositoryForm();
		form.setType("cvs");
		form.setPath("/cvsroot");
		form.setModules(null);

		BindException errors = new BindException(form, "form");

		validator.validate(form, errors);

		assertTrue("errors", errors.hasFieldErrors("modules"));
	}

}
