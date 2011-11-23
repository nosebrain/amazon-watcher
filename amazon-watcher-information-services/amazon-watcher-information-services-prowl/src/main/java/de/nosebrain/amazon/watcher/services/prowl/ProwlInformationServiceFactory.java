package de.nosebrain.amazon.watcher.services.prowl;

import java.util.Arrays;

import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;
import de.nosebrain.prowl.ProwlClient;

/**
 * 
 * @author nosebrain
 *
 */
public class ProwlInformationServiceFactory implements InformationServiceFactory {
	private ProwlClient client;

	@Override
	public InformationService createInformationService(final String settings) {
		final ProwlInfoService service = new ProwlInfoService();
		service.setClient(this.client);
		service.setApiKeys(Arrays.asList(settings.split(",")));
		return service;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(final ProwlClient client) {
		this.client = client;
	}
}
