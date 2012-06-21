package de.nosebrain.amazon.watcher.webapp.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;

/**
 * 
 * @author nosebrain
 */
public class Functions {

	/**
	 * @param item the item
	 * @return the image url for the provided asin
	 */
	public static String getImageUrl(final Item item) {
		final Amazon site = item.getSite();
		return "http://ws.assoc-amazon." + site.getTLD() + "/widgets/q?_encoding=UTF8&Format=_SL160_&ASIN=" + item.getAsin() + "&MarketPlace=" + site.toString() + "&ID=AsinImage&WS=1&ServiceVersion=20070822";
	}

	/**
	 * 
	 * @param url the url
	 * @return	the asin
	 */
	public static String getASIN(final URL url) {
		return ItemUtils.extractASIN(url);
	}

	public static String getUrl(final Item item) {
		return ItemUtils.generateUrlForItem(item); // TODO: add advice id
	}

	public static String encodeURI(final String uri) {
		try {
			return URLEncoder.encode(uri, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			// ignore
		}
		return uri;
	}
}
