package de.nosebrain.amazon.watcher.webapp.services;

import java.util.Map;

import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;

/**
 * 
 * @author nosebrain
 */
public class InformationServiceBuilder {

	private Map<String, InformationServiceFactory> informationServiceFactories;

	/**
	 * create an {@link InformationService} based on the {@link InfoService}
	 * @param infoService the infoService
	 * @return the info service
	 */
	public InformationService createInformationServiceFromInfoService(final InfoService infoService) {
		final String infoServiceKey = infoService.getInfoServiceKey();
		if (this.informationServiceFactories.containsKey(infoServiceKey)) {
			final InformationServiceFactory factory = this.informationServiceFactories.get(infoServiceKey);
			return factory.createInformationService(infoService.getSettings());
		}

		return null;
	}

	/**
	 * @param informationServiceFactories the informationServiceFactories to set
	 */
	public void setInformationServiceFactories(final Map<String, InformationServiceFactory> informationServiceFactories) {
		this.informationServiceFactories = informationServiceFactories;
	}
}
