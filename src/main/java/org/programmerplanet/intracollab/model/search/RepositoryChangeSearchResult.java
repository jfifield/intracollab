package org.programmerplanet.intracollab.model.search;

import java.util.Date;

import org.programmerplanet.intracollab.model.RepositoryChange;

/**
 * A <code>SearchResult</code> based on a <code>RepositoryChange</code>.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class RepositoryChangeSearchResult implements SearchResult {

	private RepositoryChange repositoryChange;

	public RepositoryChangeSearchResult(RepositoryChange repositoryChange) {
		this.repositoryChange = repositoryChange;
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getDate()
	 */
	public Date getDate() {
		return repositoryChange.getChangeDate();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getDescription()
	 */
	public String getDescription() {
		return "Repository change [" + repositoryChange.getId() + "] committed by " + repositoryChange.getUsername();
	}

	/**
	 * @see org.programmerplanet.intracollab.model.search.SearchResult#getText()
	 */
	public String getText() {
		return repositoryChange.getComment();
	}

}
