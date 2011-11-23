package de.nosebrain.amazon.watcher.services;

import de.nosebrain.amazon.watcher.AmazonWatcherService;

/**
 * 
 * @author nosebrain
 */
public interface SyncSerice {
	
	/**
	 * sync item with a remote service
	 */
	public void sync(final AmazonWatcherService service);
}
