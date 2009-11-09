package org.programmerplanet.intracollab.manager;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;

/**
 * Convenience <code>JpaCallback</code> implementation that returns a single
 * result given a query and optional parameters.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class SingleResultJpaCallback implements JpaCallback {

	private String queryString;
	private Map<String, Object> params;

	public SingleResultJpaCallback(String queryString) {
		this(queryString, null);
	}

	public SingleResultJpaCallback(String queryString, Map<String, Object> params) {
		this.queryString = queryString;
		this.params = params;
	}

	/**
	 * @see org.springframework.orm.jpa.JpaCallback#doInJpa(javax.persistence.EntityManager)
	 */
	public Object doInJpa(EntityManager entityManager) throws PersistenceException {
		Query query = entityManager.createQuery(queryString);
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				query.setParameter(name, value);
			}
		}
		return query.getSingleResult();
	}

}
