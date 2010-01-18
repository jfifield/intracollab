package org.programmerplanet.intracollab.model.search;

import java.util.Date;

/**
 * Interface to represent a single search result item.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public interface SearchResult {

	/**
	 * The date/time the underlying item was created.
	 */
	Date getDate();

	/**
	 * The description of the underlying item. 
	 */
	String getDescription();

	/**
	 * The full text of the underlying item in which the search matched.
	 */
	String getText();

}
