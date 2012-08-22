package de.nosebrain.amazon.watcher.webapp.util.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.nosebrain.amazon.watcher.services.InformationServiceFactory;

/**
 * 
 * @author nosebrain
 */
public class InformationServiceConfigurator {
	private PropertyPlaceholderConfigurer configurer;
	private List<String> serviceNames = Collections.emptyList();
	private Map<String, InformationServiceFactory> serviceFactories;

	/**
	 * loads the services
	 */
	public void init() {
		for (final String serviceName : this.serviceNames) {
			final String pathToConfig = "services/" + serviceName + "/" + serviceName + ".xml";
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(pathToConfig);
			this.configurer.postProcessBeanFactory(context.getBeanFactory());
			final InformationServiceFactory serviceFactory = context.getBean(InformationServiceFactory.class);
			this.serviceFactories.put(serviceName, serviceFactory);
		}
	}

	/**
	 * @param serviceFactories the services to set
	 */
	public void setServiceFactories(final Map<String, InformationServiceFactory> serviceFactories) {
		this.serviceFactories = serviceFactories;
	}

	/**
	 * @param serviceNames the serviceNames to set
	 */
	public void setServiceNames(final List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}

	/**
	 * @param configurer the configurer to set
	 */
	public void setConfigurer(final PropertyPlaceholderConfigurer configurer) {
		this.configurer = configurer;
	}
}
