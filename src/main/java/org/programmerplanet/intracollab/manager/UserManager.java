package org.programmerplanet.intracollab.manager;

import java.util.Collection;

import org.programmerplanet.intracollab.model.User;

/**
 * Primary interface for managing users.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public interface UserManager {

	Collection<User> getUsers();

	User getUser(Long id);

	User getUser(String username);

	User getUser(String username, String password);

	void saveUser(User user);

	void deleteUser(User user);

	boolean isUsernameUnique(Long id, String username);

}
