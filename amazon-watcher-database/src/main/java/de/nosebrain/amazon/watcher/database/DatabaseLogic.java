package de.nosebrain.amazon.watcher.database;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import de.nosebrain.amazon.watcher.database.util.ItemParam;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;

/**
 * 
 * @author nosebrain
 *
 */
public class DatabaseLogic {
	protected SqlSessionFactory sessionFactory;
	protected User loggedinUser;

	/**
	 * 
	 * @param session
	 * @param statement
	 * @return
	 */
	public <T> T selectOne(final SqlSession session, final String statement) {
		return this.selectOne(session, statement, null);
	}

	/**
	 * 
	 * @param session
	 * @param statement
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T selectOne(final SqlSession session, final String statement, final Object param) {
		return (T) session.selectOne(statement, param);
	}

	/**
	 * 
	 * @param session
	 * @param statement
	 * @return
	 */
	public <T> List<T> selectList(final SqlSession session, final String statement) {
		return this.selectList(session, statement, null);
	}

	/**
	 * 
	 * @param session
	 * @param statement
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> selectList(final SqlSession session, final String statement, final Object parameter) {
		final List<T> selectList = session.selectList(statement, parameter);
		return !present(selectList) ? new LinkedList<T>() : selectList;
	}

	protected Observation getObservationByItem(final Item item, final String userName, final SqlSession session) {
		final ItemParam param = new ItemParam(item);
		param.setUserName(userName);
		return this.selectOne(session, "getObservationByItemForUser", param);
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
