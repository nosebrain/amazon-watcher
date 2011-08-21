package de.nosebrain.amazon.watcher.webapp.services;

import java.util.Date;
import java.util.List;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.database.AmazonWatcherLogic;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.services.Importer;

/**
 * 
 * @author nosebrain
 */
public class ImporterService {
	
	private List<Importer> importers;
	private AmazonWatcherService service;
	
	/**
	 * imports all items from all importers
	 */
	public void importItems() {
		for (final Importer importer : this.importers) {
			final String importerId = importer.getImporterId();
			
			final Date lastSyncDate = this.service.getLastSyncDate(importerId);
			final List<Item> items = importer.getItems(lastSyncDate);
			final Date date = new Date();
			
			for (Item item : items) {
				this.service.watchItem(item);
			}
			
			this.service.updateLastSyncDate(importerId, date);
		}
	}

	/**
	 * @param importers the importers to set
	 */
	public void setImporters(List<Importer> importers) {
		this.importers = importers;
	}

	/**
	 * @param logic the logic to set
	 */
	public void setLogic(AmazonWatcherLogic logic) {
		this.service = logic;
	}
}
