package org.programmerplanet.intracollab.web.repositorychange;

import java.util.Date;

import org.programmerplanet.intracollab.model.RepositoryChange;
import org.programmerplanet.intracollab.web.project.ActivityItem;

/**
 * An <code>ActivityItem</code> for a <code>RepositoryChange</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class RepositoryChangeActivityItem implements ActivityItem {

	private RepositoryChange repositoryChange;

	public RepositoryChangeActivityItem(RepositoryChange repositoryChange) {
		this.repositoryChange = repositoryChange;
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDate()
	 */
	public Date getDate() {
		return repositoryChange.getChangeDate();
	}

	/**
	 * @see org.programmerplanet.intracollab.web.project.ActivityItem#getDescription()
	 */
	public String getDescription() {
		return "Repository change [" + repositoryChange.getId() + "] committed by " + repositoryChange.getUsername();
	}

}
