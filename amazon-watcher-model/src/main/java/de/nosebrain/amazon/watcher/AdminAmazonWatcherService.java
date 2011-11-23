package de.nosebrain.amazon.watcher;

import java.util.List;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;


/**
 * 
 * @author nosebrain
 *
 */
public interface AdminAmazonWatcherService {
	/**
	 * @return all watched items
	 */
	public List<Item> getItems();

	/**
	 * @param asin the asin of the item to update price
	 * @param price the new price
	 * @return <code>true</code> if item was updated
	 */
	public boolean updatePrice(final Item item, final float price);

	public User getUserByName(final String name);

	public boolean createUser(final User user);

	public boolean deleteUser(final String name);

	public List<User> getUsersForItem(Item item);

	public Observation getObservationByItemAndUser(String name, Item item);
}
