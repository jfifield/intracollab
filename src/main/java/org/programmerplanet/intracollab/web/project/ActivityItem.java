package org.programmerplanet.intracollab.web.project;

import java.util.Date;

/**
 * Interface to represent an item on the project activity view.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public interface ActivityItem {

	/**
	 * The date/time this activity occurred.
	 */
	Date getDate();

	/**
	 * The description of this activity.
	 */
	String getDescription();

}