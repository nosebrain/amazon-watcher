package de.nosebrain.amazon.watcher;

import java.util.List;

import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.authentication.Authority;

/**
 * 
 * @author nosebrain
 */
public interface AmazonWatcherService {

	/**
	 * 
	 * @param item
	 * @return the observation to the specified item
	 */
	public Observation getObservationByItem(Item item);

	/**
	 * @return all observations by the current logged in user
	 */
	public List<Observation> getObservations();

	/**
	 * 
	 * @param observation
	 * @return <code>true</code> if new observation was added
	 */
	public boolean addObservation(final Observation observation);

	/**
	 * updates an item (name, mode and limit)
	 * @param observation the observation to update
	 * @param item the item
	 * @return <code>true</code> if item was updated
	 */
	public boolean updateObservation(final Item item, final Observation observation);

	/**
	 * @param item the item to unwatch
	 * @return <code>true</code> iff observation was removed from list
	 */
	public boolean removeObservation(final Item item);

	/**
	 * @param authority
	 * @return <code>true</code> if authority was added to the use
	 */
	public boolean addAuthority(final Authority authority);

	/**
	 * 
	 * @param authority
	 * @return <code>true</code> if authority was updated
	 */
	public boolean updateAuthority(final Authority authority);

	/**
	 * 
	 * @param authority
	 * @return <code>true</code> if authority was removed
	 */
	public boolean removeAuthority(final Authority authority);

	public InfoService getInfoService(final String hash);

	/**
	 * @param infoService
	 * @return <code>true</code> if info service was added
	 */
	public boolean addInformationService(final InfoService infoService);

	/**
	 * update info service
	 * @param hash
	 * @param infoService
	 * @return <code>true</code> if info service was updateded
	 */
	public boolean updateInformationService(final String hash, final InfoService infoService);

	/**
	 * removes info service
	 * @param hash
	 * @return <code>true</code> if the info service was removed
	 */
	public boolean removeInformationService(final String hash);

	/**
	 * 
	 * @param item the item
	 * @return the item details (with history)
	 */
	public Item getItemDetails(final Item item);

	/**
	 * 
	 * @return the current logged in user
	 */
	public User getLoggedInUser();
	
	/**
	 * @param user the user to set
	 */
	public void setLoggedinUser(final User user);
}
