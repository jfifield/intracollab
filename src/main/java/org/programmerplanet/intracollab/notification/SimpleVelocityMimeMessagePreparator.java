package org.programmerplanet.intracollab.notification;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * <code>MimeMessagePreparator</code> implementation that prepares a
 * <code>MimeMessage</code> with HTML content generated from a Velocity
 * template.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class SimpleVelocityMimeMessagePreparator implements MimeMessagePreparator {

	private VelocityEngine velocityEngine;
	private String template;
	private Map<String, Object> model;
	private String from;
	private String to;
	private String subject;

	public SimpleVelocityMimeMessagePreparator(VelocityEngine velocityEngine, String template, Map<String, Object> model, String from, String to, String subject) {
		this.velocityEngine = velocityEngine;
		this.template = template;
		this.model = model;
		this.from = from;
		this.to = to;
		this.subject = subject;
	}

	/**
	 * @see org.springframework.mail.javamail.MimeMessagePreparator#prepare(javax.mail.internet.MimeMessage)
	 */
	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, model);
		helper.setText(text, true);
	}

}
