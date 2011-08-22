package de.nosebrain.amazon.watcher.services;


/**
 * 
 * @author nosebrain
 */
public interface Updater {

	/**
	 * @param asin the asin of the item to update
	 * @return the current price
	 */
	public Float updateItem(final String asin);
}
