package org.programmerplanet.intracollab.configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Abstract implementation of the <code>ConfigurationManager</code> interface
 * with the actual storage and retrieval of configuration options delegated to
 * subclasses.
 * 
 * This class automatically configures any registered object that implements
 * the <code>Configurable</code> interface. This occurs on initialization and
 * any time the configuration is subsequently updated.
 * 
 * @see Configurable
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public abstract class AbstractConfigurationManager implements ConfigurationManager, BeanFactoryPostProcessor {

	private static final Log log = LogFactory.getLog(AbstractConfigurationManager.class);

	private Collection<Configurable> configurables = new LinkedList<Configurable>();

	/**
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		setConfigurables(beanFactory);
		notifyConfigurables();
	}

	private void setConfigurables(ConfigurableListableBeanFactory beanFactory) {
		configurables.addAll(findConfigurables(beanFactory));
		log.info("Registered configurables: " + configurables);
	}

	private Collection<Configurable> findConfigurables(ConfigurableListableBeanFactory beanFactory) {
		Map<String, Configurable> configurableBeans = beanFactory.getBeansOfType(Configurable.class);
		return configurableBeans.values();
	}

	/**
	 * @see org.programmerplanet.intracollab.configuration.ConfigurationManager#get()
	 */
	public Map<String, String> get() {
		return load();
	}

	/**
	 * Loads the configuration options from some underlying storage.
	 */
	protected abstract Map<String, String> load();

	/**
	 * @see org.programmerplanet.intracollab.configuration.ConfigurationManager#save(java.util.Map)
	 */
	public void save(Map<String, String> configuration) {
		store(configuration);
		notifyConfigurables();
	}

	/**
	 * Persists the configuration options to some underlying storage.
	 */
	protected abstract void store(Map<String, String> configuration);

	private void notifyConfigurables() {
		Map<String, String> configuration = get();
		notifyConfigurables(configuration);
	}

	private void notifyConfigurables(Map<String, String> configuration) {
		for (Configurable configurable : configurables) {
			log.info("Configuring: " + configurable);
			configurable.configure(configuration);
		}
	}

}
