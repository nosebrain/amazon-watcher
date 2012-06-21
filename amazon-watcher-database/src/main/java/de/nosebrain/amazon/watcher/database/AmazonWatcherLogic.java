package de.nosebrain.amazon.watcher.database;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.database.util.ItemParam;
import de.nosebrain.amazon.watcher.database.util.ObservationParam;
import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.authentication.Authority;
import de.nosebrain.mybatis.MyBatisUtils;

/**
 * 
 * @author nosebrain
 */
public class AmazonWatcherLogic extends DatabaseLogic implements AmazonWatcherService {

	@Override
	public Item getItemDetails(final Item item) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return MyBatisUtils.selectOne(session, "getItemDetails", item);
		} finally {
			session.close();
		}
	}

	@Override
	public List<Observation> getObservations() {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return MyBatisUtils.selectList(session, "getAllObservationsByUser", this.loggedinUser.getName());
		} finally {
			session.close();
		}
	}

	@Override
	public Observation getObservationByItem(final Item item) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return this.getObservationByItem(item, session);
		} finally {
			session.close();
		}
	}

	/**
	 * get the observation of the current logged in user of the provided item
	 * @param item		the item
	 * @param session	the session to use
	 * @return the observation (<code>null</code> iff user is not observing item)
	 */
	protected Observation getObservationByItem(final Item item, final SqlSession session) {
		return super.getObservationByItem(item, this.loggedinUser.getName(), session);
	}

	@Override
	public boolean addObservation(final Observation observation) {
		final Item item = observation.getItem();
		final String loggedInUserName = this.loggedinUser.getName();
		final SqlSession session = this.sessionFactory.openSession();

		try {
			if (this.getObservationByItem(item, session) != null) {
				return false;
			}

			/*
			 * get item id
			 */
			Integer itemId = (Integer) session.selectOne("getItemIdByASINandSite", item);

			/*
			 * if item doesn't exist, create it
			 */
			if (itemId == null) {
				final ItemParam paramItem = new ItemParam(item);
				session.insert("insertNewItem", paramItem);
				itemId = paramItem.getId();
			}

			final ObservationParam param = new ObservationParam();
			param.setItemId(itemId);

			param.setUserName(loggedInUserName);
			param.setObservation(observation);
			session.insert("insertNewObservation", param);

			session.commit();
			return true;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean updateObservation(final Item item, final Observation observation) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			observation.setItem(item);
			if (this.getObservationByItem(item, session) == null) {
				return false;
			}

			final ObservationParam param = new ObservationParam();
			param.setObservation(observation);
			param.setUserName(this.loggedinUser.getName());

			session.update("updateObservation", param);

			session.commit();
			return true;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean removeObservation(final Item item) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			if (this.getObservationByItem(item, session) == null) {
				return false;
			}
			final ItemParam param = new ItemParam(item);
			param.setUserName(this.loggedinUser.getName());

			session.delete("deleteObservation", param);

			session.commit();
			return true;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean addAuthority(final Authority authority) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAuthority(final Authority authority) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAuthority(final Authority authority) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addInformationService(final InfoService infoService) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateInformationService(final String hash, final InfoService infoService) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeInformationService(final String hash) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getLoggedInUser() {
		return this.loggedinUser;
	}
}
