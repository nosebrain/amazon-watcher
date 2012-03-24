package de.nosebrain.amazon.watcher.webapp.services;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;
import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;

/**
 * TODO: rename
 * 
 * @author nosebrain
 */
public class InformationServiceService {
	private static final Logger log = LoggerFactory.getLogger(InformationServiceService.class);


	private AdminAmazonWatcherService service;
	private Map<String, InformationServiceFactory> informationServiceFactories;

	/**
	 * informs all user of all updated items
	 * @param updatedItems
	 */
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
					userObservations.put(user, observations);
				}
				observations.add(observation);
			}
		}

		// User => observations
		for (final Entry<User, List<Observation>> entry : userObservations.entrySet()) {
			final User user = entry.getKey();
			final List<InfoService> informationServicesForUser = user.getSettings().getInfoServices();
			final List<InformationService> userInformationServices = this.createUserInformationServices(informationServicesForUser);
			final List<Observation> observations = new LinkedList<Observation>();
			for (final Observation observation : entry.getValue()) {
				final Item item = observation.getItem();
				switch (observation.getMode()) {
				case PRICE_CHANGE:
					if (ItemUtils.getLastDelta(item) < user.getSettings().getMinDelta()) {
						continue;
					}
					break;
				case PRICE_LIMIT:
					final float limit = observation.getLimit();
					if (!ItemUtils.limit(item, limit)) {
						continue;
					}
					break;
				default:
					break;
				}
				observations.add(observation);
			}

			if (present(observations)) {
				for (final InformationService informationService : userInformationServices) {
					try {
						informationService.inform(observations);
					} catch (final Exception e) {
						log.error("error while informing user", e);
					}
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
