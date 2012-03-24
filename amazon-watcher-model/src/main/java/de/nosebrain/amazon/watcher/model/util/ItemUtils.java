package de.nosebrain.amazon.watcher.model.util;

import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.PriceHistory;

/**
 * 
 * @author nosebrain
 */
public final class ItemUtils {
	private ItemUtils() {}

	private static final Logger log = LoggerFactory.getLogger(ItemUtils.class);

	private static final String AMAZON_DOMAIN = "amazon.";
	private static final int AMAZON_DOMAIN_LENGTH = AMAZON_DOMAIN.length();
	private static final int ASIN_LENGTH = 10;

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

		log.error("can't get ASIN from url '{}'", url);
		return null;
	}

	/**
	 * 
	 * @param url
	 * @return the {@link Amazon} site of the url
	 */
	public static Amazon extractAmazon(final URL url) {
		final String host = url.getHost();
		// extract tld
		final String tld = host.substring(host.indexOf(AMAZON_DOMAIN) + AMAZON_DOMAIN_LENGTH);

		for (final Amazon amazon : Amazon.values()) {
			if (amazon.getTLD().equals(tld)) {
				return amazon;
			}
		}

		return null;
	}

	/**
	 * generates a url for the provided item
	 * @param item
	 * @return the url of the item
	 */
	public static String generateUrlForItem(final Item item) {
		return "http://" + AMAZON_DOMAIN + item.getSite().getTLD() + "/gp/product/" + item.getAsin();
	}

	public static float getLastDelta(final Item item) {
		final List<PriceHistory> priceHistories = item.getPriceHistories();

		final int size = priceHistories.size();
		if (size >= 2) {
			return priceHistories.get(size - 2).getValue() - priceHistories.get(size - 1).getValue();
		}

		// TODO: log
		return Float.MAX_VALUE;
	}

	public static boolean limit(final Item item, final float limit) {
		return belowLimit(item, limit) || overLimit(item, limit);
	}

	public static boolean belowLimit(final Item item, final float limit) {
		final List<PriceHistory> priceHistories = item.getPriceHistories();
		final int size = priceHistories.size();
		if (size < 2) {
			return false;
		}

		final float current = priceHistories.get(size - 1).getValue();
		final float before = priceHistories.get(size - 2).getValue();

		return before >= limit && current < limit;
	}

	public static boolean overLimit(final Item item, final float limit) {
		final List<PriceHistory> priceHistories = item.getPriceHistories();
		final int size = priceHistories.size();
		if (size < 2) {
			return false;
		}

		final float current = priceHistories.get(size - 1).getValue();
		final float before = priceHistories.get(size - 2).getValue();

		return before < limit && current >= limit;
	}
}
