package de.nosebrain.amazon.watcher.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.core.io.InputStreamResource;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.spring.beans.SqlSessionFactoryFactoryBean;

/**
 * 
 * @author nosebrain
 */
public class AmazonWatcherLogicTest {

	public static void main(final String[] args) throws Exception {
		final AmazonWatcherLogic logic = new AmazonWatcherLogic();
		logic.setLoggedinUser(new User("nosebrain"));
		final BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("axacodix");
		dataSource.setUrl("jdbc:mysql://localhost/amazon-watcher?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8&amp;mysqlEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull");

		final SqlSessionFactoryFactoryBean bean = new SqlSessionFactoryFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfigLocation(new InputStreamResource(AmazonWatcherLogic.class.getClassLoader().getResourceAsStream("de/nosebrain/amazon/watcher/database/amazon-watcher-database.xml")));
		final SqlSessionFactory sessionFactory = bean.getObject();
		logic.setSessionFactory(sessionFactory);

		final AdminAmazonWatcherLogic adminLogic = new AdminAmazonWatcherLogic();
		adminLogic.setSessionFactory(sessionFactory);
		final Item item = new Item();
		item.setAsin("B005S6JMB4");
		item.setSite(Amazon.DE);
		final User nosebrain = adminLogic.getUserByName("nosebrain");
		nosebrain.setApiKey("");
	}
}
