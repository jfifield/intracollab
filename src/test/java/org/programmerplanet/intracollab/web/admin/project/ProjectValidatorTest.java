package org.programmerplanet.intracollab.web.admin.project;

import org.easymock.EasyMock;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Project;
import org.springframework.validation.BindException;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ProjectValidatorTest extends TestCase {

	public void testValidate_Good() {
		ProjectValidator validator = new ProjectValidator();

		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.isProjectNameUnique(null, "Test Name")).andReturn(Boolean.TRUE);
		EasyMock.replay(projectManager);
		validator.setProjectManager(projectManager);

		Project project = new Project();
		project.setName("Test Name");

		BindException errors = new BindException(project, "project");

		validator.validate(project, errors);

		assertFalse("errors", errors.hasErrors());
	}

	public void testValidate_MissingName() {
		ProjectValidator validator = new ProjectValidator();

		Project project = new Project();
		project.setName(null);

		BindException errors = new BindException(project, "project");

		validator.validate(project, errors);

		assertTrue("errors", errors.hasFieldErrors("name"));
	}

	public void testValidate_DuplicateName() {
		ProjectValidator validator = new ProjectValidator();

		ProjectManager projectManager = EasyMock.createMock(ProjectManager.class);
		EasyMock.expect(projectManager.isProjectNameUnique(null, "Test Name")).andReturn(Boolean.FALSE);
		EasyMock.replay(projectManager);
		validator.setProjectManager(projectManager);

		Project project = new Project();
		project.setName("Test Name");

		BindException errors = new BindException(project, "project");

		validator.validate(project, errors);

		assertTrue("errors", errors.hasFieldErrors("name"));
	}

}
