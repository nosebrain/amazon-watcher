package de.nosebrain.amazon.watcher;

import java.util.Date;
import java.util.List;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public interface AmazonWatcherService {
	
	/**
	 * @return all watched items
	 */
	public List<Item> getItems();
	
	/**
	 * @param item the item to watch
	 * @return <code>true</code> iff item was added to list
	 */
	public boolean watchItem(final Item item);
	
	/**
	 * updates an item (mode, and limit)
	 * @param url the url of the item to update
	 * @param item the update information
	 * @return <code>true</code> if item was updated
	 */
	public boolean updateItem(final String asin, final Item item);
	
	/**
	 * @param item the item to unwatch
	 * @return <code>true</code> iff item was removed from list
	 */
	public boolean unwatchItem(final Item item);
	
	/**
	 * @param importerId the importer id
	 * @return the last date the importer was last called
	 */
	public Date getLastSyncDate(final String importerId);
	
	/**
	 * @param importerId the importer id
	 * @param date the date to set
	 * @return <code>true</code> iff the last sync date was set successfully
	 */
	public boolean updateLastSyncDate(final String importerId, final Date date);
}
