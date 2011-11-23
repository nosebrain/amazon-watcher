package de.nosebrain.amazon.watcher.webapp.services;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;
import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;

/**
 * 
 * @author nosebrain
 *
 */
public class InformationServiceService {

	private AdminAmazonWatcherService service;
	private Map<String, InformationServiceFactory> informationServiceFactories;

	public void informUsers(final List<Item> updatedItems) {
		final Map<User, List<Observation>> userObservations = new LinkedHashMap<User, List<Observation>>();
		for (final Item item : updatedItems) {
			final List<User> users = this.service.getUsersForItem(item);
			for (final User user : users) {
				final Observation observation = this.service.getObservationByItemAndUser(user.getName(), item);
				final List<Observation> observations;
				if (userObservations.containsKey(user)) {
					observations = userObservations.get(user);
				} else {
					observations = new LinkedList<Observation>();
				}
				observations.add(observation);
			}
		}

		// User => observations
		for (final Entry<User, List<Observation>> entry : userObservations.entrySet()) {
			final User user = entry.getKey();
			final List<InfoService> informationServicesForUser = user.getSettings().getInfoServices();

			final List<InformationService> userInformationServices = this.createUserInformationServices(informationServicesForUser);
			for (final InformationService informationService : userInformationServices) {
				try {
					informationService.inform(user, entry.getValue());
				} catch (final Exception e) {
					// TODO: log
				}
			}
		}
	}

	private List<InformationService> createUserInformationServices(final List<InfoService> informationServicesForUser) {
		final List<InformationService> informationServices = new LinkedList<InformationService>();
		for (final InfoService infoService : informationServicesForUser) {
			final String infoServiceId = infoService.getInfoServiceKey();
			if (this.informationServiceFactories.containsKey(infoServiceId)) {
				final InformationServiceFactory factory = this.informationServiceFactories.get(infoServiceId);
				final InformationService informationService = factory.createInformationService(infoService.getSettings());

				if (informationService != null) {
					informationServices.add(informationService);
				} else {
					// TODO: log
				}
			} else {
				// TODO: log
			}
		}

		return informationServices;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(final AdminAmazonWatcherService service) {
		this.service = service;
	}

	/**
	 * @param informationServiceFactories the informationServiceFactories to set
	 */
	public void setInformationServiceFactories(
			final Map<String, InformationServiceFactory> informationServiceFactories) {
		this.informationServiceFactories = informationServiceFactories;
	}
}
