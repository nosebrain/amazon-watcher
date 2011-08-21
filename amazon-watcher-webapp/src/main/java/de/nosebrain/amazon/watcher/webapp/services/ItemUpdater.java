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
public class ItemUpdater {
	
	private AmazonWatcherService service;
	private List<InformationService> informationServices;
	private Updater updater;
	
	/**
	 * updates all items in the service
	 */
	public void updateItems() {
		final List<Item> allItems = this.service.getItems();
		final List<Item> updatedItems = new LinkedList<Item>();
		for (Item item : allItems) {
			final float lastPrice = item.getLastPrice();
			this.updater.updateItem(item);
			
			if (lastPrice != item.getLastPrice()) {
				/*
				 * update last price
				 */
				this.service.updateItem(item.getAsin(), item);
				if (item.getMode().equals(WatchMode.PRICE_CHANGE)) {
					updatedItems.add(item);
				}
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
