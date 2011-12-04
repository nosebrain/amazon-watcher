package de.nosebrain.amazon.watcher.services;

import de.nosebrain.amazon.watcher.AmazonWatcherService;

/**
 * 
 * @author nosebrain
 */
public interface SyncSerice {

	/**
	 * sync item with a remote service
	 * @param service the sync service to use
	 */
	public void sync(final AmazonWatcherService service);
}
