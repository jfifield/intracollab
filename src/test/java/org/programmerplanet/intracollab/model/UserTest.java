package org.programmerplanet.intracollab.model;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserTest extends TestCase {

	public void testGetPasswordHash() {
		String password = "TestPassword";
		String hash = User.getPasswordHash(password);
		assertEquals("hash", "23fd44228071730e3457dc5de887b3ae", hash);
	}

}
