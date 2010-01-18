package org.programmerplanet.intracollab.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.search.SearchResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class SearchController extends SimpleFormController {

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(java.lang.Object)
	 */
	protected ModelAndView onSubmit(Object command) throws Exception {
		SearchForm form = (SearchForm)command;
		String query = form.getQuery();
		List<SearchResult> results = projectManager.search(query);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(this.getCommandName(), command);
		model.put("results", results);
		return new ModelAndView(getSuccessView(), model);
	}

}
