package org.programmerplanet.intracollab.web.admin.configuration;

import java.util.HashMap;
import java.util.Map;

import org.programmerplanet.intracollab.notification.DefaultNotificationManager;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class ConfigurationForm {

	private Map<String, String> configuration = new HashMap<String, String>();

	public void setConfiguration(Map<String, String> configuration) {
		this.configuration = configuration;
	}

	public Map<String, String> getConfiguration() {
		return configuration;
	}

	public void setEmailNotifications(boolean emailNotifications) {
		configuration.put(DefaultNotificationManager.CONFIG_EMAIL_NOTIFICATIONS, Boolean.toString(emailNotifications));
	}

	public boolean isEmailNotifications() {
		return Boolean.valueOf(configuration.get(DefaultNotificationManager.CONFIG_EMAIL_NOTIFICATIONS));
	}

	public void setSmtpHost(String smtpHost) {
		configuration.put(DefaultNotificationManager.CONFIG_SMTP_HOST, smtpHost);
	}

	public String getSmtpHost() {
		return configuration.get(DefaultNotificationManager.CONFIG_SMTP_HOST);
	}

	public void setSmtpUsername(String smtpUsername) {
		configuration.put(DefaultNotificationManager.CONFIG_SMTP_USERNAME, smtpUsername);
	}

	public String getSmtpUsername() {
		return configuration.get(DefaultNotificationManager.CONFIG_SMTP_USERNAME);
	}

	public void setSmtpPassword(String smtpPassword) {
		configuration.put(DefaultNotificationManager.CONFIG_SMTP_PASSWORD, smtpPassword);
	}

	public String getSmtpPassword() {
		return configuration.get(DefaultNotificationManager.CONFIG_SMTP_PASSWORD);
	}

	public void setEmailSender(String emailSender) {
		configuration.put(DefaultNotificationManager.CONFIG_EMAIL_SENDER, emailSender);
	}

	public String getEmailSender() {
		return configuration.get(DefaultNotificationManager.CONFIG_EMAIL_SENDER);
	}

}
