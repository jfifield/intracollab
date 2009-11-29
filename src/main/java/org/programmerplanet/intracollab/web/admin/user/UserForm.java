package org.programmerplanet.intracollab.web.admin.user;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2009 Joseph Fifield
 */
public class UserForm {

	private Long id;
	private String username;
	private String password1;
	private String password2;
	private String emailAddress;
	private boolean administrator;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword2() {
		return password2;
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

}
