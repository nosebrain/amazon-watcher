package de.nosebrain.amazon.watcher.services.prowl;

import java.util.Arrays;

import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;

/**
 * 
 * @author nosebrain
 */
public abstract class ProwlInformationServiceFactory implements InformationServiceFactory {

	@Override
	public InformationService createInformationService(final String settings) {
		final ProwlInfoService service = this.createService();
		service.setApiKeys(Arrays.asList(settings));
		return service;
	}

	protected abstract ProwlInfoService createService();
}
