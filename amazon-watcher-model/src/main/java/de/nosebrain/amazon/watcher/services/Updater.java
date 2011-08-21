package de.nosebrain.amazon.watcher.services;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public interface Updater {

	/**
	 * @param item the item to update
	 * @return TODO: improve documentation
	 */
	public boolean updateItem(final Item item);
}
