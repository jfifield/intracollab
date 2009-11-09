package org.programmerplanet.intracollab.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.programmerplanet.intracollab.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * JPA implementation of the <code>UserManager</code> interface.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class JpaUserManager extends JpaDaoSupport implements UserManager {

	/**
	 * @see org.programmerplanet.intracollab.manager.UserManager#getUsers()
	 */
	public Collection<User> getUsers() {
		return this.getJpaTemplate().find("SELECT e FROM User AS e");
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.UserManager#getUser(java.lang.Long)
	 */
	public User getUser(Long id) {
		return this.getJpaTemplate().find(User.class, id);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.UserManager#getUser(java.lang.String, java.lang.String)
	 */
	public User getUser(String username, String password) {
		String query = "SELECT u FROM User AS u WHERE u.username = :username AND u.password = :password";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", User.getPasswordHash(password));
		List users = this.getJpaTemplate().findByNamedParams(query, params);
		User user = null;
		if (!users.isEmpty()) {
			if (users.size() == 1) {
				user = (User)users.get(0);
			} else {
				throw new IncorrectResultSizeDataAccessException(1, users.size());
			}
		}
		return user;
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.UserManager#saveUser(org.programmerplanet.intracollab.model.User)
	 */
	public void saveUser(User user) {
		this.getJpaTemplate().merge(user);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.UserManager#deleteUser(org.programmerplanet.intracollab.model.User)
	 */
	public void deleteUser(User user) {
		user = this.getJpaTemplate().getReference(User.class, user.getId());
		this.getJpaTemplate().remove(user);
	}

	/**
	 * @see org.programmerplanet.intracollab.manager.UserManager#isUsernameUnique(java.lang.Long, java.lang.String)
	 */
	public boolean isUsernameUnique(Long id, String username) {
		String query = "SELECT COUNT(*) FROM User WHERE username = :username AND id <> :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("id", id != null ? id : Integer.valueOf(0));
		SingleResultJpaCallback callback = new SingleResultJpaCallback(query, params);
		Number count = (Number)this.getJpaTemplate().execute(callback);
		return count.intValue() == 0;
	}

}
