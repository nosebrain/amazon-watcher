package de.nosebrain.amazon.watcher.webapp.services;

import java.util.LinkedList;
import java.util.List;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
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
	
	/**
	 * updates all items in the service
	 */
	public void updateItems() {
		final List<Item> allItems = this.service.getItems();
		final List<Item> updatedItems = new LinkedList<Item>();
		
		for (final Item item : allItems) {
			final String asin = item.getAsin();
			final Float currentPrice = this.updater.updateItem(asin);
			final Float lastPrice = item.getCurrentPrice();
			
			if (currentPrice != null && (lastPrice == null) || lastPrice.compareTo(currentPrice) != 0) {			
				final WatchMode mode = item.getMode();
				
				switch (mode) {
				case PRICE_CHANGE:
					updatedItems.add(item);
					break;
				case PRICE_LIMIT:
					// TODO: npe?
					final float limit = item.getLimit();
					/*
					 * current price under limit => inform
					 */
					if (currentPrice != null && currentPrice < limit) {
						updatedItems.add(item);
					}
					
					/*
					 * current price over limit, but last price was under limit
					 */
					if (currentPrice != null && currentPrice >= limit && lastPrice != null && lastPrice < limit) {
						updatedItems.add(item);
					}
					break;
				}
				
				/*
				 * update current price
				 */
				item.setLastPrice(lastPrice);
				item.setCurrentPrice(currentPrice);
				
				this.service.updateItem(asin, item);
			}
		}
		
		/*
		 * inform user with config services
		 */
		for (final InformationService service : this.informationServices) {
			service.inform(updatedItems);
		}
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
}
