package de.nosebrain.amazon.watcher;

import java.util.List;

import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.authentication.Authority;

/**
 * 
 * @author nosebrain
 */
public interface AmazonWatcherService {

	/**
	 * 
	 * @param item
	 * @return
	 */
	public Observation getObservationByItem(Item item);

	/**
	 * @return all observations
	 */
	public List<Observation> getObservations();

	/**
	 * 
	 * @param observation
	 * @return
	 */
	public boolean addObservation(final Observation observation);

	/**
	 * updates an item (mode, and limit)
	 * @param asin the asin of the item to update
	 * @param item the update information
	 * @return <code>true</code> if item was updated
	 */
	public boolean updateObservation(final Item item, final Observation observation);

	/**
	 * @param item the item to unwatch
	 * @return <code>true</code> iff item was removed from list
	 */
	public boolean removeObservation(final Item item);

	/**
	 * @param authority
	 * @return
	 */
	public boolean addAuthority(final Authority authority);

	/**
	 * 
	 * @param authority
	 * @return
	 */
	public boolean updateAuthority(final Authority authority);

	/**
	 * 
	 * @param authority
	 * @return
	 */
	public boolean removeAuthority(final Authority authority);

	/**
	 * @param infoService
	 * @return
	 */
	public boolean addInformationService(final InfoService infoService);

	/**
	 * update info service
	 * @param hash
	 * @param infoService
	 * @return
	 */
	public boolean updateInformationService(final String hash, final InfoService infoService);

	/**
	 * removes info service
	 * @param hash
	 * @return
	 */
	public boolean removeInformationService(final String hash);
}
