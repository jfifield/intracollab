package org.programmerplanet.intracollab.web.user;

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
import org.programmerplanet.intracollab.manager.UserManager;
import org.programmerplanet.intracollab.model.User;
import org.programmerplanet.intracollab.model.activity.ActivityItem;
import org.programmerplanet.intracollab.util.DateRange;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class UserActivityController implements Controller {

	private static final int DEFAULT_DAYS = 45;

	private UserManager userManager;
	private ProjectManager projectManager;

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @see org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		User user = userManager.getUser(username);
		DateRange dateRange = getDateRange();

		List<org.programmerplanet.intracollab.model.activity.ActivityItem> activityItems = projectManager.getUserActivity(user, dateRange);
		Map<Date, List<ActivityItem>> activityItemsByDay = groupActivityItemsByDay(activityItems);

		List<Date> days = new LinkedList<Date>(activityItemsByDay.keySet());
		Collections.sort(days);
		Collections.reverse(days);

		Collection<List<ActivityItem>> values = activityItemsByDay.values();
		for (List<ActivityItem> list : values) {
			Collections.sort(list, new PropertyComparator("date", false, false));
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		model.put("days", days);
		model.put("activityItemsByDay", activityItemsByDay);

		return new ModelAndView("user/activity", model);
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
