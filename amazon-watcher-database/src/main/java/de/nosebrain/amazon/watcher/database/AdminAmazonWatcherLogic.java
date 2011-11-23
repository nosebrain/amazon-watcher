package de.nosebrain.amazon.watcher.database;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;
import de.nosebrain.amazon.watcher.database.util.ItemParam;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.model.UserSettings;

/**
 * 
 * @author nosebrain
 *
 */
public class AdminAmazonWatcherLogic extends DatabaseLogic implements AdminAmazonWatcherService {

	@Override
	public List<Item> getItems() {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return this.selectList(session, "getAllItems");
		} finally {
			session.close();
		}
	}

	@Override
	public boolean updatePrice(final Item item, final float price) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			final Integer itemId = this.selectOne(session, "getItemIdByASINandSite", item);
			if (itemId == null) {
				return false;
			}

			final ItemParam priceParam = new ItemParam();
			priceParam.setPrice(price);
			priceParam.setId(itemId);

			session.insert("insertPrice", priceParam);
			session.commit();
			return true;
		} finally {
			session.close();
		}
	}

	@Override
	public User getUserByName(final String name) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return this.getUserByName(name, session);
		} finally {
			session.close();
		}
	}

	private User getUserByName(final String name, final SqlSession session) {
		return this.selectOne(session, "getUserByName", name);
	}

	@Override
	public boolean createUser(final User user) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			if (this.getUserByName(user.getName(), session) != null) {
				return false;
			}

			user.setSettings(UserSettings.getDefaultSettings());
			session.insert("insertUser", user);
			session.commit();

			return true;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean deleteUser(final String name) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			if (this.getUserByName(name, session) == null) {
				return false;
			}

			session.delete("deleteObservationForUser", name);
			session.delete("deleteUser", name);

			session.commit();
			return true;
		} finally {
			session.close();
		}
	}

	@Override
	public List<User> getUsersForItem(final Item item) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return this.selectList(session, "getAllUsersByItem", item);
		} finally {
			session.close();
		}
	}

	@Override
	public Observation getObservationByItemAndUser(final String name, final Item item) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return this.getObservationByItem(item, name, session);
		} finally {
			session.close();
		}
	}

}
