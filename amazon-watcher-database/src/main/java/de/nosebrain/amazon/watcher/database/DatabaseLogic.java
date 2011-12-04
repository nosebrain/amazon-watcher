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
	protected SqlSessionFactory sessionFactory;
	protected User loggedinUser;

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
