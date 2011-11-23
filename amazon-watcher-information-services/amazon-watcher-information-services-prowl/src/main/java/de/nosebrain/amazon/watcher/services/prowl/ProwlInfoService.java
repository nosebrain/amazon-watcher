package de.nosebrain.amazon.watcher.services.prowl;

import java.io.IOException;
import java.util.List;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.model.UserSettings;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.prowl.Notification;
import de.nosebrain.prowl.Notification.NotificationBuilder;
import de.nosebrain.prowl.ProwlClient;

/**
 * TODO: i18n
 * @author nosebrain
 */
public class ProwlInfoService implements InformationService {

	private List<String> apiKeys;
	private ProwlClient client;

	@Override
	public void inform(final User user, final List<Observation> observations) {
		for (final Observation observation : observations) {
			try {
				final Notification notification = this.createNotification(user, observation);
				if (notification != null) {
					this.client.sendNotification(notification, this.apiKeys);
				}
			} catch (final IOException e) {
				// TODO: log exception
			}
		}
	}

	/**
	 * @param user
	 * @param observation the item to use to build the notification
	 * @return the notification to send
	 */
	protected Notification createNotification(final User user, final Observation observation) {
		final UserSettings settings = user.getSettings();

		final Item item = observation.getItem();
		final int size = item.getPriceHistories().size();

		final float currentPrice;
		if (size > 0) {
			currentPrice = item.getPriceHistories().get(size - 1).getValue();
		} else {
			// TODO: log illegal state
			currentPrice = -1.0f;
		}

		final float lastPrice;
		if (size > 1) {
			lastPrice = item.getPriceHistories().get(size - 2).getValue();
		} else {
			lastPrice = -1.0f;
		}

		final String description;

		switch (observation.getMode()) {
		case PRICE_CHANGE:
			if (ItemUtils.getLastDelta(item) < settings.getMinDelta()) {
				return null;
			}

			final StringBuilder change = new StringBuilder("Price changed. ");
			change.append(lastPrice > currentPrice ? "Dropped" : "Increased");
			change.append(" from ");
			change.append(lastPrice);
			change.append(" to ");
			change.append(currentPrice);
			change.append(".");
			description = change.toString();
			break;
		case PRICE_LIMIT:
			final StringBuilder limit = new StringBuilder("The price dropped under your limit. It's now ");
			limit.append(currentPrice);
			limit.append(".");
			description = limit.toString();
			break;
		default:
			description = "something happend";
			break;
		}
		return new NotificationBuilder()
		.application("Amazon Watcher") // TODO: config
		.event(observation.getName())
		.description(description)
		.url(ItemUtils.generateUrlForItem(item)).build();
	}

	/**
	 * @param apiKeys the apiKeys to set
	 */
	public void setApiKeys(final List<String> apiKeys) {
		this.apiKeys = apiKeys;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(final ProwlClient client) {
		this.client = client;
	}
}
