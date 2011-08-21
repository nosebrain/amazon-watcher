package de.nosebrain.amazon.watcher.services;

import java.util.Date;
import java.util.List;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public interface Importer {
	
	/**
	 * @param lastSyncDate the last time the importer was called
	 * @return get items to import
	 */
	public List<Item> getItems(final Date lastSyncDate);
	
	/**
	 * @return the id of the importer
	 */
	public String getImporterId();
}
