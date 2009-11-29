package org.programmerplanet.intracollab.web.admin.milestone;

import java.util.Date;

import org.programmerplanet.intracollab.model.Milestone;
import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class MilestoneValidatorTest extends TestCase {

	public void testValidate_Good() {
		MilestoneValidator validator = new MilestoneValidator();

		Milestone milestone = new Milestone();
		milestone.setName("Test Name");
		milestone.setDueDate(new Date());

		BindException errors = new BindException(milestone, "milestone");

		validator.validate(milestone, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingName() {
		MilestoneValidator validator = new MilestoneValidator();

		Milestone milestone = new Milestone();
		milestone.setName(null);
		milestone.setDueDate(new Date());

		BindException errors = new BindException(milestone, "milestone");

		validator.validate(milestone, errors);

		assertTrue("errors", errors.hasFieldErrors("name"));
	}

	public void testValidate_MissingDueDate() {
		MilestoneValidator validator = new MilestoneValidator();

		Milestone milestone = new Milestone();
		milestone.setName("Test Name");
		milestone.setDueDate(null);

		BindException errors = new BindException(milestone, "milestone");

		validator.validate(milestone, errors);

		assertTrue("errors", errors.hasFieldErrors("dueDate"));
	}

}
