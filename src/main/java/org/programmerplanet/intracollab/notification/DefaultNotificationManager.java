package org.programmerplanet.intracollab.notification;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.programmerplanet.intracollab.configuration.Configurable;
import org.programmerplanet.intracollab.model.Attachment;
import org.programmerplanet.intracollab.model.Comment;
import org.programmerplanet.intracollab.model.Ticket;
import org.programmerplanet.intracollab.model.TicketChange;
import org.programmerplanet.intracollab.model.User;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * Default implementation of the <code>NotificationManager</code> interface.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class DefaultNotificationManager implements NotificationManager, Configurable {

	private static final String CONFIG_EMAIL_NOTIFICATIONS = "email.notifications";
	private static final String CONFIG_EMAIL_SENDER = "email.sender";
	private static final String CONFIG_SMTP_HOST = "smtp.host";
	private static final String CONFIG_SMTP_USERNAME = "smtp.username";
	private static final String CONFIG_SMTP_PASSWORD = "smtp.password";

	private VelocityEngine velocityEngine;
	private JavaMailSender mailSender = new NullJavaMailSender();
	private String from;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * @see org.programmerplanet.intracollab.configuration.Configurable#configure(java.util.Map)
	 */
	public void configure(Map<String, String> configuration) {
		String notifications = configuration.get(CONFIG_EMAIL_NOTIFICATIONS);
		if (Boolean.parseBoolean(notifications)) {
			this.mailSender = createJavaMailSender(configuration);
		} else {
			this.mailSender = new NullJavaMailSender();
		}
		this.from = configuration.get(CONFIG_EMAIL_SENDER);
	}

	private JavaMailSender createJavaMailSender(Map<String, String> configuration) {
		String host = configuration.get(CONFIG_SMTP_HOST);
		String username = configuration.get(CONFIG_SMTP_USERNAME);
		String password = configuration.get(CONFIG_SMTP_PASSWORD);
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(host);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		return javaMailSender;
	}

	/**
	 * @see org.programmerplanet.intracollab.notification.NotificationManager#sendTicketCreateNotification(org.programmerplanet.intracollab.model.Ticket)
	 */
	public void sendTicketCreateNotification(Ticket ticket) {
		User user = ticket.getAssignedTo();
		if (user != null) {
			if (!user.getUsername().equals(ticket.getCreatedBy())) {
				String to = user.getEmailAddress();
				String subject = ticket.getProject().getName() + " ticket #" + ticket.getId() + " created by " + ticket.getCreatedBy();
				String template = "/org/programmerplanet/intracollab/notification/ticket_create.html.vm";
				Map<String, Object> model = createDefaultVelocityModel();
				model.put("ticket", ticket);
				MimeMessagePreparator mimeMessagePreparator = new SimpleVelocityMimeMessagePreparator(velocityEngine, template, model, this.from, to, subject);
				mailSender.send(mimeMessagePreparator);
			}
		}
	}

	/**
	 * @see org.programmerplanet.intracollab.notification.NotificationManager#sendTicketChangeNotification(org.programmerplanet.intracollab.model.TicketChange)
	 */
	public void sendTicketChangeNotification(TicketChange ticketChange) {
		User user = ticketChange.getTicket().getAssignedTo();
		if (user != null) {
			if (!user.getUsername().equals(ticketChange.getUsername())) {
				String to = user.getEmailAddress();
				String subject = ticketChange.getTicket().getProject().getName() + " ticket #" + ticketChange.getTicket().getId() + " modified by " + ticketChange.getUsername();
				String template = "/org/programmerplanet/intracollab/notification/ticket_change.html.vm";
				Map<String, Object> model = createDefaultVelocityModel();
				model.put("ticketChange", ticketChange);
				model.put("ticket", ticketChange.getTicket());
				MimeMessagePreparator mimeMessagePreparator = new SimpleVelocityMimeMessagePreparator(velocityEngine, template, model, this.from, to, subject);
				mailSender.send(mimeMessagePreparator);
			}
		}
	}

	/**
	 * @see org.programmerplanet.intracollab.notification.NotificationManager#sendCommentNotification(org.programmerplanet.intracollab.model.Comment)
	 */
	public void sendCommentNotification(Comment comment) {
		User user = comment.getTicket().getAssignedTo();
		if (user != null) {
			if (!user.getUsername().equals(comment.getCreatedBy())) {
				String to = user.getEmailAddress();
				String subject = comment.getTicket().getProject().getName() + " ticket #" + comment.getTicket().getId() + " commented by " + comment.getCreatedBy();
				String template = "/org/programmerplanet/intracollab/notification/comment.html.vm";
				Map<String, Object> model = createDefaultVelocityModel();
				model.put("comment", comment);
				model.put("ticket", comment.getTicket());
				MimeMessagePreparator mimeMessagePreparator = new SimpleVelocityMimeMessagePreparator(velocityEngine, template, model, this.from, to, subject);
				mailSender.send(mimeMessagePreparator);
			}
		}
	}

	/**
	 * @see org.programmerplanet.intracollab.notification.NotificationManager#sendAttachmentNotification(org.programmerplanet.intracollab.model.Attachment)
	 */
	public void sendAttachmentNotification(Attachment attachment) {
		User user = attachment.getTicket().getAssignedTo();
		if (user != null) {
			if (!user.getUsername().equals(attachment.getCreatedBy())) {
				String to = user.getEmailAddress();
				String subject = attachment.getTicket().getProject().getName() + " ticket #" + attachment.getTicket().getId() + " attachment by " + attachment.getCreatedBy();
				String template = "/org/programmerplanet/intracollab/notification/attachment.html.vm";
				Map<String, Object> model = createDefaultVelocityModel();
				model.put("attachment", attachment);
				model.put("ticket", attachment.getTicket());
				MimeMessagePreparator mimeMessagePreparator = new SimpleVelocityMimeMessagePreparator(velocityEngine, template, model, this.from, to, subject);
				mailSender.send(mimeMessagePreparator);
			}
		}
	}

	/**
	 * Creates a default velocity model map containing the velocity date tool.
	 */
	private Map<String, Object> createDefaultVelocityModel() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("date", new DateTool());
		return model;
	}

	/**
	 * No-op implementation of the <code>JavaMailSender</code> interface to be
	 * used as a place holder when there is no real <code>JavaMailSender</code>.
	 */
	private static class NullJavaMailSender implements JavaMailSender {
		public MimeMessage createMimeMessage() {
			return null;
		}

		public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
			return null;
		}

		public void send(MimeMessage mimeMessage) throws MailException {
		}

		public void send(MimeMessage[] mimeMessages) throws MailException {
		}

		public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
		}

		public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
		}

		public void send(SimpleMailMessage simpleMessage) throws MailException {
		}

		public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		}
	}

}
