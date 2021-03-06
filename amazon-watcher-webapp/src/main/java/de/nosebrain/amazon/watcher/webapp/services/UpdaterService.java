package de.nosebrain.amazon.watcher.webapp.services;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.PriceHistory;
import de.nosebrain.amazon.watcher.services.Updater;

/**
 * 
 * @author nosebrain
 */
public class UpdaterService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdaterService.class);

	private AdminAmazonWatcherService service;
	private Updater updater;
	private InformationServiceService informationServiceService;

	// TODO: store in database
	private Date lastUpdateDate;

	/**
	 * updates all items in the service
	 */
	public void updateItems() {
		final Date now = new Date();
		final List<Item> allItems = this.service.getItems();
		final List<Item> updatedItems = new LinkedList<Item>();

		for (final Item item : allItems) {
			try {
				final List<PriceHistory> priceHistories = item.getPriceHistories();
				final int historySize = priceHistories.size();

				final Float currentPrice = this.updater.updateItem(item);
				Float lastPrice = null;
				if (historySize > 0) {
					lastPrice = priceHistories.get(historySize - 1).getValue();
				}

				if (currentPrice != null) {
					if (lastPrice == null || lastPrice.compareTo(currentPrice) != 0) {
						/*
						 * only update the price the first time
						 */
						this.service.updatePrice(item, currentPrice);
						final PriceHistory history = new PriceHistory();
						history.setDate(now);
						history.setValue(currentPrice);
						item.getPriceHistories().add(history);
						if (present(lastPrice)) {
							updatedItems.add(item);
						}
					}
				}
			} catch (final Exception e) {
				LOGGER.error("error updating item {}", item.getAsin(), e);
			}
		}
		this.lastUpdateDate = now;

		this.informationServiceService.informUsers(updatedItems);
	}

	/**
	 * @param service the service to set
	 */
	public void setService(final AdminAmazonWatcherService service) {
		this.service = service;
	}

	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(final Updater updater) {
		this.updater = updater;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	/**
	 * @param informationServiceService the informationServiceService to set
	 */
	public void setInformationServiceService(final InformationServiceService informationServiceService) {
		this.informationServiceService = informationServiceService;
	}
}
