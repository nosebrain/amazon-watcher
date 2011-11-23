package de.nosebrain.amazon.watcher.services;

import de.nosebrain.amazon.watcher.model.Item;


/**
 * 
 * @author nosebrain
 */
public interface Updater {

	/**
	 * @param item the item to update
	 * @return the current price
	 */
	public Float updateItem(final Item item);
}
