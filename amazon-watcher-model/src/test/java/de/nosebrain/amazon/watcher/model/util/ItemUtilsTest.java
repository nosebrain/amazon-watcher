package de.nosebrain.amazon.watcher.model.util;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class ItemUtilsTest {
	
	@Test
	public void extractASIN() throws MalformedURLException {
		assertEquals("B00411RW82", ItemUtils.extractASIN(new URL("http://www.amazon.de/Firefly-Aufbruch-Serenity-komplette-Serie/dp/B00411RW82/ref=sr_1_1?ie=UTF8&qid=1313960848&sr=8-1")));
		assertEquals("3551684111", ItemUtils.extractASIN(new URL("http://www.amazon.de/gp/product/3551684111/ref=s9_simh_gw_p14_d0_g14_i2?pf_rd_m=A3JWKAKR8XB7XF&pf_rd_s=center-2&pf_rd_r=1F9ZVMRZRMDR3NXWX8GS&pf_rd_t=101&pf_rd_p=463375173&pf_rd_i=301128")));
	}
}
