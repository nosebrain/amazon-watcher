package de.nosebrain.amazon.watcher.services.prowl;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.prowl.Notification;
import de.nosebrain.prowl.Notification.NotificationBuilder;
import de.nosebrain.prowl.ProwlClient;

/**
 * {@link InformationService} for Prowl (http://prowlapp.com) notifications
 * 
 * @author nosebrain
 */
public class ProwlInfoService implements InformationService {
	private static final Logger log = LoggerFactory.getLogger(ProwlInfoService.class);

	private MessageSource messageSource;
	private String systemName;
	private List<String> apiKeys;
	private ProwlClient client;

	@Override
	public void testService(final Locale locale) throws Exception {
		final Notification notification = new NotificationBuilder()
		.application(this.systemName)
		.event(this.messageSource.getMessage("testmessage.event", null, locale))
		.description(this.messageSource.getMessage("testmessage.description", null, locale))
		.build();
		this.client.sendNotification(notification, this.apiKeys);
	}

	@Override
	public void inform(final List<Observation> observations, final Locale locale) {
		for (final Observation observation : observations) {
			try {
				final Notification notification = this.createNotification(observation, locale);
				if (notification != null) {
					this.client.sendNotification(notification, this.apiKeys);
				}
			} catch (final IOException e) {
				// TODO: collect exceptions and throw exception later to move logging
				log.error("error while sending notification", e);
			}
		}
	}

	/**
	 * @param observation the item to use to build the notification
	 * @param locale
	 * @return the notification to send
	 */
	protected Notification createNotification(final Observation observation, final Locale locale) {
		final Item item = observation.getItem();
		final NumberFormat numberFormat = NumberFormat.getNumberInstance(item.getSite().getNumberFormatLocale());
		numberFormat.setMinimumFractionDigits(2);
		
		final int size = item.getPriceHistories().size();

		final float currentPrice;
		if (size > 0) {
			currentPrice = item.getPriceHistories().get(size - 1).getValue();
		} else {
			log.error("illegal item state (currentPrice) for item (asin = {}, site = {})", item.getAsin(), item.getSite());
			currentPrice = -1.0f;
		}

		final float lastPrice;
		if (size > 1) {
			lastPrice = item.getPriceHistories().get(size - 2).getValue();
		} else {
			log.error("illegal item state (lastPrice) for item (asin = {}, site = {})", item.getAsin(), item.getSite());
			lastPrice = -1.0f;
		}

		final String currency = item.getSite().getCurrency();
		final String description;
		final Object[] args = new Object[] { currency, numberFormat.format(lastPrice), numberFormat.format(currentPrice) };

		switch (observation.getMode()) {
		case PRICE_CHANGE:
			if (lastPrice > currentPrice) {
				description = this.messageSource.getMessage("message.description.change.dropped", args, locale);
			} else {
				description = this.messageSource.getMessage("message.description.change.increased", args, locale);
			}
			break;
		case PRICE_LIMIT:
			if (ItemUtils.overLimit(item, observation.getLimit())) {
				description = this.messageSource.getMessage("message.description.limit.over", args, locale);
			} else {
				description = this.messageSource.getMessage("message.description.limit.under", args, locale);
			}
			break;
		default:
			description = "something happend";
			break;
		}
		return new NotificationBuilder()
		.application(this.systemName)
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

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
