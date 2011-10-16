package de.nosebrain.amazon.watcher.webapp.services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.PriceHistory;
import de.nosebrain.amazon.watcher.model.WatchMode;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.Updater;

/**
 * 
 * @author nosebrain
 */
public class UpdaterService {
	
	private AmazonWatcherService service;
	private List<InformationService> informationServices;
	private Updater updater;
	
	private Date lastUpdateDate;
	
	/**
	 * updates all items in the service
	 */
	public void updateItems() {
		final List<Item> allItems = this.service.getItems();
		final List<Item> updatedItems = new LinkedList<Item>();
		
		for (final Item item : allItems) {
			final List<PriceHistory> priceHistories = item.getPriceHistories();
			final int historySize = priceHistories.size();
			
			final String asin = item.getAsin();
			final Float currentPrice = this.updater.updateItem(asin);
			Float lastPrice = null;
			if (historySize > 0) {
				lastPrice = priceHistories.get(historySize - 1).getValue();
			}
			
			if (currentPrice != null) {
				if (lastPrice == null) {
					/*
					 * only update the price the first time
					 */
					this.service.updatePrice(asin, currentPrice);
				} else if (lastPrice.compareTo(currentPrice) != 0) {
					final WatchMode mode = item.getMode();
					
					switch (mode) {
					case PRICE_CHANGE:
						updatedItems.add(item);
						break;
					case PRICE_LIMIT:
						final float limit = item.getLimit();
						/*
						 * current price under limit => inform
						 */
						if (currentPrice < limit) {
							updatedItems.add(item);
						}
						
						/*
						 * current price over limit, but last price was under limit
						 */
						if (currentPrice >= limit && lastPrice < limit) {
							updatedItems.add(item);
						}
						break;
					}
					
					final PriceHistory history = new PriceHistory();
					history.setValue(currentPrice);
					history.setDate(new Date());
					priceHistories.add(history);
					
					/*
					 * update current price
					 */
					this.service.updatePrice(asin, currentPrice);
				}
			} else {
				// TODO: log error
			}
		}
		
		/*
		 * inform user with config services
		 */
		if (!updatedItems.isEmpty()) {
			for (final InformationService service : this.informationServices) {
				service.inform(updatedItems);
				
			}
		}
		
		this.lastUpdateDate = new Date();
	}

	/**
	 * @param service the service to set
	 */
	public void setService(AmazonWatcherService service) {
		this.service = service;
	}

	/**
	 * @param informationServices the informationServices to set
	 */
	public void setInformationServices(List<InformationService> informationServices) {
		this.informationServices = informationServices;
	}

	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(Updater updater) {
		this.updater = updater;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
}
