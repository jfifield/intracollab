package org.programmerplanet.intracollab.web.admin.component;

import org.programmerplanet.intracollab.model.Component;
import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ComponentValidatorTest extends TestCase {

	public void testValidate_Good() {
		ComponentValidator validator = new ComponentValidator();

		Component component = new Component();
		component.setName("Test Name");

		BindException errors = new BindException(component, "component");

		validator.validate(component, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingName() {
		ComponentValidator validator = new ComponentValidator();

		Component component = new Component();
		component.setName(null);

		BindException errors = new BindException(component, "component");

		validator.validate(component, errors);

		assertTrue("errors", errors.hasFieldErrors("name"));
	}

}
