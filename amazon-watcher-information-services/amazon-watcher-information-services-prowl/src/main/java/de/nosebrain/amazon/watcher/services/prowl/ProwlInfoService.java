package de.nosebrain.amazon.watcher.services.prowl;

import java.io.IOException;
import java.util.List;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.prowl.Notification;
import de.nosebrain.prowl.Notification.NotificationBuilder;
import de.nosebrain.prowl.ProwlClient;

/**
 * 
 * @author nosebrain
 */
public class ProwlInfoService implements InformationService {

	private List<String> apiKeys;
	private ProwlClient client;
	
	@Override
	public void inform(final List<Item> items) {
		for (final Item item : items) {
			// TODO: improve info
			try {
				final Notification notification = new NotificationBuilder().application("Amazon Watcher").event(item.getName()).description(String.valueOf(item.getCurrentPrice())).url(item.getUrl().toString()).build();
				this.client.sendNotification(notification, this.apiKeys);
			} catch (final IOException e) {
				// TODO: log exception
			}
		}
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
