package de.nosebrain.amazon.watcher.database;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.core.io.InputStreamResource;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.ObservationMode;
import de.nosebrain.amazon.watcher.model.PriceHistory;
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

		final List<Observation> observations = logic.getObservations();

		observations.size();
		final List<PriceHistory> priceHistories = observations.get(0).getItem().getPriceHistories();
		final int size = priceHistories.size();
		final float firstValue = priceHistories.get(0).getValue();

		final Observation observation = new Observation();
		observation.setMode(ObservationMode.PRICE_LIMIT);
		observation.setLimit(10.55f);
		observation.setName("Qnap 410");
		final Item item = new Item();
		item.setSite(Amazon.DE);
		item.setAsin("B002PML096");
		observation.setItem(item);

		logic.addObservation(observation);
	}
}
