package de.nosebrain.amazon.watcher.updater.util;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.updater.WebSiteUpdater;

/**
 * test for the web site updater
 * NOTE: this test may be fail often
 * 
 * @author nosebrain
 */
public class WebSiteUpdaterTest {

	private static final WebSiteUpdater UPDATER = new WebSiteUpdater();

	/**
	 * tests {@link WebSiteUpdater#updateItem(Item)}
	 */
	@Test
	public void testGermanSite() {
		final Item item = new Item();
		item.setSite(Amazon.DE);
		item.setAsin("B00377ISA2");
		final Float updateItem = UPDATER.updateItem(item);
		assertEquals(14.99, updateItem, 0.1);
	}

	/**
	 * tests {@link WebSiteUpdater#updateItem(Item)}
	 * with multiple pages
	 */
	@Test
	public void testGermanSite2() {
		final Item item = new Item();
		item.setSite(Amazon.DE);
		item.setAsin("B000JQVE5U");
		final Float updateItem = UPDATER.updateItem(item);
		assertEquals(14.97, updateItem, 0.1);
	}

	/**
	 * tests {@link WebSiteUpdater#updateItem(Item)}
	 * with multiple pages
	 */
	@Test
	public void testGermanSite3() {
		final Item item = new Item();
		item.setSite(Amazon.DE);
		item.setAsin("B005YYTHZG");
		final Float updateItem = UPDATER.updateItem(item);
		assertEquals(Float.NaN, updateItem, 0.1);
	}

	/**
	 * tests {@link WebSiteUpdater#updateItem(Item)}
	 */
	@Test
	public void testUSSite() {
		final Item item = new Item();
		item.setSite(Amazon.US);
		item.setAsin("B005L9E7J0");

		final Float price = UPDATER.updateItem(item);
		assertEquals(148.98, price, 0.1);
	}
}
