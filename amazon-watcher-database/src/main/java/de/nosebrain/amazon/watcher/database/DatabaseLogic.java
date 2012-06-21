package de.nosebrain.amazon.watcher.database;



import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import de.nosebrain.amazon.watcher.database.util.ItemParam;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.mybatis.MyBatisUtils;

/**
 * 
 * @author nosebrain
 *
 */
public class DatabaseLogic {
	/** the sql session factory to use */
	protected SqlSessionFactory sessionFactory;
	/** the current logged in user */
	protected User loggedinUser;

	/**
	 * get the observation of the provided user of the provided item
	 * @param item		the item
	 * @param userName 	the name of the user
	 * @param session	the session to use
	 * @return the observation (<code>null</code> iff user is not observing item)
	 */
	protected Observation getObservationByItem(final Item item, final String userName, final SqlSession session) {
		final ItemParam param = new ItemParam(item);
		param.setUserName(userName);
		return MyBatisUtils.selectOne(session, "getObservationByItemForUser", param);
	}

	/**
	 * @param loggedinUser the loggedinUser to set
	 */
	public void setLoggedinUser(final User loggedinUser) {
		this.loggedinUser = loggedinUser;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(final SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
