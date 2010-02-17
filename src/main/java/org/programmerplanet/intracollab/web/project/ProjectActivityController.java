package org.programmerplanet.intracollab.web.project;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.programmerplanet.intracollab.manager.ProjectManager;
import org.programmerplanet.intracollab.model.Project;
import org.programmerplanet.intracollab.model.activity.ActivityItem;
import org.programmerplanet.intracollab.util.DateRange;
import org.programmerplanet.intracollab.web.ServletRequestUtils;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class ProjectActivityController implements Controller {

	private static final int DEFAULT_DAYS = 45;

	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = ServletRequestUtils.getLongParameter(request, "id");
		Project project = id != null ? projectManager.getProject(id) : null;
		DateRange dateRange = getDateRange();

		List<ActivityItem> activityItems = (project != null) ? projectManager.getProjectActivity(project, dateRange) : projectManager.getActivity(dateRange);
		Map<Date, List<ActivityItem>> activityItemsByDay = groupActivityItemsByDay(activityItems);

		List<Date> days = new LinkedList<Date>(activityItemsByDay.keySet());
		Collections.sort(days);
		Collections.reverse(days);

		Collection<List<ActivityItem>> values = activityItemsByDay.values();
		for (List<ActivityItem> list : values) {
			Collections.sort(list, new PropertyComparator("date", false, false));
		}

		List<Project> projects = new LinkedList<Project>(projectManager.getProjects());
		Collections.sort(projects, new PropertyComparator("name", false, true));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("project", project);
		model.put("projects", projects);
		model.put("days", days);
		model.put("activityItemsByDay", activityItemsByDay);

		return new ModelAndView("project/activity", model);
	}

	private Map<Date, List<ActivityItem>> groupActivityItemsByDay(List<ActivityItem> activityItems) {
		Map<Date, List<ActivityItem>> activityItemsByDay = new HashMap<Date, List<ActivityItem>>();
		for (ActivityItem activityItem : activityItems) {
			Date day = DateUtils.truncate(activityItem.getDate(), Calendar.DATE);
			List<ActivityItem> activityItemsForDay = activityItemsByDay.get(day);
			if (activityItemsForDay == null) {
				activityItemsForDay = new LinkedList<ActivityItem>();
				activityItemsByDay.put(day, activityItemsForDay);
			}
			activityItemsForDay.add(activityItem);
		}
		return activityItemsByDay;
	}

	/**
	 * Gets the date range to display activity for.
	 */
	private DateRange getDateRange() {
		Date end = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(end);
		calendar.add(Calendar.DATE, -DEFAULT_DAYS);
		Date start = calendar.getTime();

		return new DateRange(start, end);
	}

}
