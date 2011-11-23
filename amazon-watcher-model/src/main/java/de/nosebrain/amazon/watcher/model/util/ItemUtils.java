package de.nosebrain.amazon.watcher.model.util;

import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.PriceHistory;

/**
 * 
 * @author nosebrain
 */
public final class ItemUtils {

	private static final int ASIN_LENGTH = 10;

	private ItemUtils() {}

	/**
	 * extracts the ASIN from the url
	 * 
	 * @param url the url to use
	 * @return the ASIN
	 */
	public static String extractASIN(final URL url) {
		if (url == null) {
			return null;
		}
		final String path = url.getPath();
		final StringTokenizer tokenizer = new StringTokenizer(path, "/");
		if (tokenizer.hasMoreTokens()) {
			final String first = tokenizer.nextToken();
			if ("gp".equals(first)) {
				if (tokenizer.hasMoreTokens()) {
					final String product = tokenizer.nextToken();
					if ("product".equals(product) && tokenizer.hasMoreTokens()) {
						final String asin = tokenizer.nextToken();
						if (asin.length() == ASIN_LENGTH) {
							return asin;
						}
					}
				}
			} else {
				if (tokenizer.hasMoreTokens()) {
					final String dp = tokenizer.nextToken();
					if ("dp".equals(dp) && tokenizer.hasMoreElements()) {
						final String asin = tokenizer.nextToken();

						if (asin.length() == ASIN_LENGTH) {
							return asin;
						}
					}
				}
			}
		}

		// TODO: log
		return null;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static Amazon extractAmazon(final URL url) {
		final String host = url.getHost();
		// extract tld
		final String tld = host.substring(host.indexOf("amazon.") + "amazon.".length());

		for (final Amazon amazon : Amazon.values()) {
			if (amazon.getTLD().equals(tld)) {
				return amazon;
			}
		}

		return null;
	}

	public static String generateUrlForItem(final Item item) {
		return "http://amazon." + item.getSite().getTLD() + "/gp/product/" + item.getAsin();
	}

	public static float getLastDelta(final Item item) {
		final List<PriceHistory> priceHistories = item.getPriceHistories();

		if (priceHistories.size() >= 2) {
			return priceHistories.get(1).getValue() - priceHistories.get(0).getValue();
		}

		// TODO: log
		return Float.MAX_VALUE;
	}
}
