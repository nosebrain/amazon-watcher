package de.nosebrain.amazon.watcher.updater;

import static de.nosebrain.util.ValidationUtils.present;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.ItemCondition;
import de.nosebrain.amazon.watcher.model.Seller;
import de.nosebrain.amazon.watcher.services.Updater;

/**
 * 
 * @author nosebrain
 */
public class WebSiteUpdater implements Updater {
	private static final Logger log = LoggerFactory.getLogger(WebSiteUpdater.class);

	private static String getUrlForItem(final Item item) {
		final String host = getHost(item);
		return host + "/gp/offer-listing/" + item.getAsin();
	}

	private static String getHost(final Item item) {
		return "http://www.amazon." + item.getSite().getTLD();
	}

	private static interface ItemParser {

		public String cleanPriceString(final String priceString);

		public ItemCondition getCondition(String conditionString);
	}


	/**
	 * the client to use
	 */
	private final HttpClient client = new DefaultHttpClient();

	@Override
	public Float updateItem(final Item item) {
		try {
			return this.update(item);
		} catch (final IOException e) {
			log.error("error updating item " + item, e);
		}
		return Float.NaN;
	}

	private Float update(final Item item) throws IOException {
		HttpEntity entity = null;
		try {
			final String url = getUrlForItem(item);
			final HttpGet get = new HttpGet(url);
			final HttpResponse response = this.client.execute(get);

			entity = response.getEntity();
			final InputStream content = entity.getContent();
			final Map<ItemCondition, Float> amazon = extractPrice(item, content).get(Seller.AMAZON);
			return amazon.get(ItemCondition.NEW);
		} finally {
			EntityUtils.consume(entity);
		}
	}

	private static Map<Seller, Map<ItemCondition, Float>> extractPrice(final Item item, final InputStream content) {
		final Map<Seller, Map<ItemCondition, Float>> prices = new HashMap<Seller, Map<ItemCondition,Float>>();

		final ItemParser parser = getParser(item.getSite());

		try {
			final Document offerPage = Jsoup.parse(content, "UTF-8", getHost(item));
			final Elements offers = offerPage.select(".result");
			for (final Element offer : offers) {
				final Element priceElement = offer.select(".price").first();
				final String rawText = parser.cleanPriceString(priceElement.text());
				final float price = Float.parseFloat(rawText);

				Seller seller = Seller.THIRD_PARTY;
				if (offer.select(".ratingHeader").size() == 0) {
					seller = Seller.AMAZON;
				}

				ItemCondition condition = ItemCondition.NEW;
				final Element conditionElement = offer.select(".condition").first();
				if (present(conditionElement)) {
					final String conditionString = conditionElement.text().trim().toLowerCase();
					condition = parser.getCondition(conditionString);
				}

				Map<ItemCondition, Float> conditionMap = prices.get(seller);
				if (!present(conditionMap)) {
					conditionMap = new HashMap<ItemCondition, Float>();
					prices.put(seller, conditionMap);
				}

				if (conditionMap.containsKey(condition)) {
					final Float lastPrice = conditionMap.get(condition);
					if (price < lastPrice) {
						conditionMap.put(condition, price);
					}
				} else {
					conditionMap.put(condition, price);
				}
			}

		} catch (final IOException e) {
			log.error("can't parse offer site for item {}", item, e);
		}
		return prices;
	}

	private static ItemParser getParser(final Amazon site) {
		if (site == Amazon.DE) {
			return new ItemParser() {

				@Override
				public String cleanPriceString(final String priceString) {
					return priceString.replace("EUR", "").trim().replace(',', '.');
				}

				@Override
				public ItemCondition getCondition(final String conditionString) {
					if ("neu".equals(conditionString)) {
						return ItemCondition.NEW;
					}
					if (conditionString.contains("gebraucht")) {
						return ItemCondition.USED;
					}
					return null;
				}
			};
		}

		if (site == Amazon.US) {
			return new ItemParser() {

				@Override
				public String cleanPriceString(final String priceString) {
					return priceString.replace("$", "").replaceAll(",", "");
				}

				@Override
				public ItemCondition getCondition(final String conditionString) {
					if ("new".equals(conditionString)) {
						return ItemCondition.NEW;
					}

					if (conditionString.contains("used")) {
						return ItemCondition.USED;
					}
					return null;
				}
			};
		}

		return null;
	}


}
