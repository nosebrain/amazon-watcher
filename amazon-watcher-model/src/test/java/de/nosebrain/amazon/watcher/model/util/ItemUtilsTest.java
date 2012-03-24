package de.nosebrain.amazon.watcher.model.util;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.junit.Test;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.PriceHistory;

/**
 * 
 * @author nosebrain
 */
public class ItemUtilsTest {

	/**
	 * tests {@link ItemUtils#extractASIN(URL)}
	 * @throws MalformedURLException ignore
	 */
	@Test
	public void extractASIN() throws MalformedURLException {
		assertEquals("B00411RW82", ItemUtils.extractASIN(new URL("http://www.amazon.de/Firefly-Aufbruch-Serenity-komplette-Serie/dp/B00411RW82/ref=sr_1_1?ie=UTF8&qid=1313960848&sr=8-1")));
		assertEquals("3551684111", ItemUtils.extractASIN(new URL("http://www.amazon.de/gp/product/3551684111/ref=s9_simh_gw_p14_d0_g14_i2?pf_rd_m=A3JWKAKR8XB7XF&pf_rd_s=center-2&pf_rd_r=1F9ZVMRZRMDR3NXWX8GS&pf_rd_t=101&pf_rd_p=463375173&pf_rd_i=301128")));
	}

	@Test
	public void testExtractAmazon() throws MalformedURLException {
		assertEquals(Amazon.DE, ItemUtils.extractAmazon(new URL("http://amazon.de/gp/product/2FB0051VVOB2")));
		assertEquals(Amazon.DE, ItemUtils.extractAmazon(new URL("http://www.amazon.de/Firefly-Aufbruch-Serenity-komplette-Serie/dp/B00411RW82/ref=sr_1_1?ie=UTF8&qid=1313960848&sr=8-1")));
		assertEquals(Amazon.US, ItemUtils.extractAmazon(new URL("http://www.amazon.com/Netgear-Wireless-Gigabit-Router-WNDR4500/dp/B005KG44V0/ref=amb_link_358719862_2?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=right-4&pf_rd_r=0ZPGP1KSFRTPK85DYW6X&pf_rd_t=101&pf_rd_p=1330425902&pf_rd_i=507846")));
	}

	@Test
	public void testGenerateUrlForItem() {
		final Item item = new Item();
		final Amazon site = Amazon.DE;
		item.setSite(site);
		final String asin = "123123123";
		item.setAsin(asin);
		assertEquals("http://amazon." + site.getTLD() + "/gp/product/" + asin, ItemUtils.generateUrlForItem(item));
	}

	@Test
	public void testOverLimit() {
		final Item item = new Item();
		final PriceHistory history1 = new PriceHistory();
		history1.setValue(9.99f);

		final PriceHistory history2 = new PriceHistory();
		history2.setValue(12);

		item.setPriceHistories(Arrays.asList(history2, history1));

		assertFalse(ItemUtils.overLimit(item, 11));
		assertTrue(ItemUtils.belowLimit(item, 11));

		item.setPriceHistories(Arrays.asList(history1, history2));
		assertTrue(ItemUtils.overLimit(item, 11));
		assertFalse(ItemUtils.belowLimit(item, 11));
	}
}
