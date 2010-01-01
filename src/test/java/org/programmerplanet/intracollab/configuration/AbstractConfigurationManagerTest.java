package org.programmerplanet.intracollab.configuration;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import junit.framework.TestCase;

/**
 * Comment goes here...
 * 
 * @author <a href="jfifield@programmerplanet.org">Joseph Fifield</a>
 * 
 * Copyright (c) 2010 Joseph Fifield
 */
public class AbstractConfigurationManagerTest extends TestCase {

	public void testPostProcessBeanFactory() {
		Map<String, Configurable> configurables = getTestConfigurables();

		ConfigurableListableBeanFactory beanFactory = EasyMock.createMock(ConfigurableListableBeanFactory.class);
		EasyMock.expect(beanFactory.getBeansOfType(Configurable.class)).andReturn(configurables);
		EasyMock.replay(beanFactory);

		Map<String, String> configuration = getTestConfiguration();
		TestConfigurationManager configurationManager = new TestConfigurationManager(configuration);
		configurationManager.postProcessBeanFactory(beanFactory);

		TestConfigurable testConfigurable1 = (TestConfigurable)configurables.get("testConfigurable1");
		TestConfigurable testConfigurable2 = (TestConfigurable)configurables.get("testConfigurable2");

		assertEquals("configuration", configuration, testConfigurable1.getConfiguration());
		assertEquals("configuration", configuration, testConfigurable2.getConfiguration());
	}

	public void testGet() {
		Map<String, String> configuration = getTestConfiguration();
		TestConfigurationManager configurationManager = new TestConfigurationManager(configuration);
		assertEquals("configuration", configuration, configurationManager.get());
	}

	public void testSave() {
		Map<String, Configurable> configurables = getTestConfigurables();

		ConfigurableListableBeanFactory beanFactory = EasyMock.createMock(ConfigurableListableBeanFactory.class);
		EasyMock.expect(beanFactory.getBeansOfType(Configurable.class)).andReturn(configurables);
		EasyMock.replay(beanFactory);

		Map<String, String> configuration = getTestConfiguration();
		TestConfigurationManager configurationManager = new TestConfigurationManager(configuration);
		configurationManager.postProcessBeanFactory(beanFactory);

		TestConfigurable testConfigurable1 = (TestConfigurable)configurables.get("testConfigurable1");
		TestConfigurable testConfigurable2 = (TestConfigurable)configurables.get("testConfigurable2");

		assertEquals("configuration", configuration, testConfigurable1.getConfiguration());
		assertEquals("configuration", configuration, testConfigurable2.getConfiguration());

		Map<String, String> newConfiguration = new HashMap<String, String>();
		newConfiguration.putAll(configuration);
		newConfiguration.put("option3", "value3");

		configurationManager.save(newConfiguration);

		assertEquals("new configuration", newConfiguration, testConfigurable1.getConfiguration());
		assertEquals("new configuration", newConfiguration, testConfigurable2.getConfiguration());
	}

	private Map<String, String> getTestConfiguration() {
		Map<String, String> configuration = new HashMap<String, String>();
		configuration.put("option1", "value1");
		configuration.put("option2", "value2");
		return configuration;
	}

	private Map<String, Configurable> getTestConfigurables() {
		Map<String, Configurable> configurables = new HashMap<String, Configurable>();
		configurables.put("testConfigurable1", new TestConfigurable());
		configurables.put("testConfigurable2", new TestConfigurable());
		return configurables;
	}

	private static class TestConfigurationManager extends AbstractConfigurationManager {

		private Map<String, String> configuration = new HashMap<String, String>();

		public TestConfigurationManager(Map<String, String> configuration) {
			this.configuration = configuration;
		}

		protected Map<String, String> load() {
			return configuration;
		}

		protected void store(Map<String, String> configuration) {
			this.configuration.putAll(configuration);
		}

	}

	private static class TestConfigurable implements Configurable {

		private Map<String, String> configuration;

		public Map<String, String> getConfiguration() {
			return configuration;
		}

		public void configure(Map<String, String> configuration) {
			this.configuration = new HashMap<String, String>(configuration);
		}

	}

}
