package de.nosebrain.amazon.watcher.database;



import static de.nosebrain.util.ValidationUtils.present;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import de.nosebrain.amazon.watcher.database.util.InfoServiceParam;
import de.nosebrain.amazon.watcher.database.util.ItemParam;
import de.nosebrain.amazon.watcher.database.util.UserAwareParam;
import de.nosebrain.amazon.watcher.model.InfoService;
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
		final UserAwareParam param = new ItemParam(item);
		param.setUserName(userName);
		return MyBatisUtils.selectOne(session, "getObservationByItemForUser", param);
	}

	protected InfoService getInfoServiceByHash(final SqlSession session, final UserAwareParam param) {
		return MyBatisUtils.selectOne(session, "getInfoServiceByHash", param);
	}

	protected boolean createInfoService(final InfoService infoService, final SqlSession session) {
		infoService.recalculateHash();
		final InfoServiceParam param = new InfoServiceParam(infoService);
		param.setUserName(this.loggedinUser.getName());
		param.setHash(infoService.getHash());

		final InfoService infoServiceInDatabase = this.getInfoServiceByHash(session, param);
		if (present(infoServiceInDatabase)) {
			return false;
		}

		session.insert("insertInfoService", param);
		return true;
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
