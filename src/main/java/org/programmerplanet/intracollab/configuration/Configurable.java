package org.programmerplanet.intracollab.configuration;

import java.util.Map;

/**
 * The <code>Configurable</code> interface can be implemented by classes that
 * wish to be notified with initial and updated configuration.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public interface Configurable {

	/**
	 * Configure the object with any applicable configuration options. This will
	 * automatically be called once during initialization of the configuration
	 * system and any time the configuration is updated.
	 */
	void configure(Map<String, String> configuration);

}
