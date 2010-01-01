package org.programmerplanet.intracollab.configuration;

import java.util.Map;

/**
 * Primary interface for managing the system configuration.
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public interface ConfigurationManager {

	Map<String, String> get();

	void save(Map<String, String> configuration);

}
