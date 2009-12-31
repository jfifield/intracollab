package org.programmerplanet.intracollab.web.admin.sourcerepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.CvsSourceRepository;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.SourceRepository;
import org.programmerplanet.intracollab.util.Pair;
import org.programmerplanet.intracollab.web.ServletRequestUtils;
import org.programmerplanet.intracollab.web.spring.SimpleMultiActionFormController;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SourceRepositoryEditController extends SimpleMultiActionFormController {

	private static final String TYPE_NONE = "none";
	private static final String TYPE_CVS = "cvs";

	private static final List<Pair<String, String>> TYPE_OPTIONS = new ArrayList<Pair<String, String>>() {
		{
			add(new Pair<String, String>(TYPE_NONE, "None"));
			add(new Pair<String, String>(TYPE_CVS, "CVS"));
		}
	};

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Long id = ServletRequestUtils.getRequiredLongParameter(request, "project_id");
		Project project = projectManager.getProject(id, "sourceRepository");
		SourceRepository sourceRepository = null;
		SourceRepositoryForm form = (SourceRepositoryForm)command;

		if (TYPE_NONE.equals(form.getType())) {
			sourceRepository = null;
		} else {
			sourceRepository = project.getSourceRepository();
			if (TYPE_CVS.equals(form.getType()) && !(sourceRepository instanceof CvsSourceRepository)) {
				sourceRepository = new CvsSourceRepository();
			}
			BeanUtils.copyProperties(form, sourceRepository);
		}

		project.setSourceRepository(sourceRepository);
		projectManager.saveProject(project);

		return new ModelAndView(this.getSuccessView(), "id", id);
	}

	public ModelAndView cancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Long id = ServletRequestUtils.getRequiredLongParameter(request, "project_id");
		return new ModelAndView(this.getSuccessView(), "id", id);
	}

	/**
	 * @see org.springframework.web.servlet.mvc.BaseCommandController#suppressBinding(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean suppressBinding(HttpServletRequest request) {
		return !WebUtils.hasSubmitParameter(request, "__save");
	}

	/**
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Long id = ServletRequestUtils.getRequiredLongParameter(request, "project_id");
		Project project = projectManager.getProject(id, "sourceRepository");
		SourceRepository sourceRepository = project.getSourceRepository();
		SourceRepositoryForm form = new SourceRepositoryForm();

		if (sourceRepository == null) {
			form.setType(TYPE_NONE);
		} else {
			BeanUtils.copyProperties(sourceRepository, form);
			if (sourceRepository instanceof CvsSourceRepository) {
				form.setType(TYPE_CVS);
			}
		}

		return form;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		Long id = ServletRequestUtils.getRequiredLongParameter(request, "project_id");
		Project project = projectManager.getProject(id);
		data.put("project", project);
		data.put("typeOptions", TYPE_OPTIONS);
		return data;
	}

}
