package de.nosebrain.amazon.watcher.database;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.database.util.PriceParam;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;

/**
 * 
 * @author nosebrain
 */
public class AmazonWatcherLogic implements AmazonWatcherService {
	
	private SqlSessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Item> getItems() {
		final SqlSession session = sessionFactory.openSession();
		try {
			return session.selectList("getAllWatchedItems");
		} finally {
			session.close();
		}
	}
	
	@Override
	public Item getItemByAsin(String asin) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			return this.getItemByASIN(asin, session);
		} finally {
			session.close();
		}
	}

	public boolean watchItem(final Item item) {
		final String asin = ItemUtils.extractASIN(item.getUrl());
		item.setAsin(asin);
		
		final SqlSession session = sessionFactory.openSession();
		try {
			final Item watchedItem = this.getItemByASIN(asin, session);
			if (watchedItem != null) {
				return false;
			}
			
			session.insert("insertItem", item);
			session.commit();
			return true;
		} finally {
			session.close();
		}
	}
	
	private Item getItemByASIN(final String asin, SqlSession session) {
		return (Item) session.selectOne("getItemByASIN", asin);
	}

	public boolean unwatchItem(final Item item) {
		final String asin = item.getAsin();
		final SqlSession session = sessionFactory.openSession();
		try {
			if (this.getItemByASIN(asin, session) == null) {
				return false;
			}
			
			session.delete("deleteItem", asin);
			session.commit();
			return true;
		} finally {
			session.close();
		}
	}
	
	@Override
	public boolean updateItem(final String asin, Item item) {
		// set asign to the asign to update
		item.setAsin(asin);
		final SqlSession session = sessionFactory.openSession();
		try {
			if (this.getItemByASIN(asin, session) == null) {
				return false;
			}
			
			session.update("updateItem", item);
			session.commit();			
			return true;
		} finally {
			session.close();
		}
	}
	
	@Override
	public boolean updatePrice(String asin, float price) {
		final SqlSession session = this.sessionFactory.openSession();
		try {
			if (this.getItemByASIN(asin, session) == null) {
				return false;
			}
			final PriceParam priceParam = new PriceParam();
			priceParam.setPrice(price);
			priceParam.setAsin(asin);
			
			session.insert("insertPrice", priceParam);
			session.commit();
			return true;
		} finally {
			session.close();
		}
	}
	
	@Override
	public Date getLastSyncDate(String importerId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean updateLastSyncDate(String importerId, Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
