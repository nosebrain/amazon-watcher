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
	 * @param asin the id of the item
	 * @return the item with the requested id or null if the item doesn't exists
	 */
	public Item getItemByAsin(String asin);
	
	/**
	 * @param item the item to watch
	 * @return <code>true</code> iff item was added to list
	 */
	public boolean watchItem(final Item item);
	
	/**
	 * updates an item (mode, and limit)
	 * @param asin the asin of the item to update
	 * @param item the update information
	 * @return <code>true</code> if item was updated
	 */
	public boolean updateItem(final String asin, final Item item);
	
	/**
	 * @param asin the asin of the item to update price
	 * @param price the new price
	 * @return <code>true</code> if item was updated
	 */
	public boolean updatePrice(final String asin, final float price);
	
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
