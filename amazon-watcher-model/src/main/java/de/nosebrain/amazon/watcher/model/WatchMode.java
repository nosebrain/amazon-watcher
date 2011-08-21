package de.nosebrain.amazon.watcher.model;

/**
 * 
 * @author nosebrain
 */
public enum WatchMode {

	/**
	 * decrease and increase of a price
	 */
	PRICE_CHANGE,
	
	/**
	 * < value
	 */
	PRICE_LIMIT;
}
