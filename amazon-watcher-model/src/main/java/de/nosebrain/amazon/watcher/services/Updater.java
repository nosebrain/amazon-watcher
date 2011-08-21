package de.nosebrain.amazon.watcher.services;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public interface Updater {

	/**
	 * @param item the item to update
	 */
	public void updateItem(final Item item);
}
