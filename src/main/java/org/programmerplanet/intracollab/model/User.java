package org.programmerplanet.intracollab.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a user of the system.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class User extends BaseEntity {

	private String username;
	private String password;
	private String emailAddress;
	private boolean administrator;

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public static String getPasswordHash(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		md.update(password.getBytes());
		byte[] digest = md.digest();
		BigInteger i = new BigInteger(1, digest);
		String hash = i.toString(16);
		while (hash.length() < 32) {
			hash = "0" + hash;
		}
		return hash;
	}

}
