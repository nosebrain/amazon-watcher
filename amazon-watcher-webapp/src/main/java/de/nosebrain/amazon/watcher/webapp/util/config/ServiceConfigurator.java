package de.nosebrain.amazon.watcher.webapp.util.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.nosebrain.amazon.watcher.services.InformationService;

/**
 * 
 * @author nosebrain
 */
public class ServiceConfigurator {
	private List<String> serviceNames = Collections.emptyList();
	private List<InformationService> services;
	
	/**
	 * loads the services
	 */
	public void init() {
		for (String serviceName : this.serviceNames) {
			final String pathToConfig = "services/" + serviceName + "/" + serviceName + ".xml";
			final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(pathToConfig);			
			final InformationService service = context.getBean(InformationService.class);
			this.services.add(service);
		}
	}
	
	/**
	 * @param services the services to set
	 */
	public void setServices(List<InformationService> services) {
		this.services = services;
	}

	/**
	 * @param serviceNames the serviceNames to set
	 */
	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}
}
