package de.nosebrain.amazon.watcher.updater;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.services.Updater;
import de.nosebrain.amazon.watcher.updater.util.SignedRequestsHelper;

/**
 * 
 * @author nosebrain
 */
@Deprecated // please use the web site updater
public class ProductAdvertisingAPIUpdater implements Updater {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private static final XPathExpression offersExpression;
	private static final XPathExpression priceExpression;

	static {
		final XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			offersExpression = xpath.compile("/ItemLookupResponse/Items/Item/Offers/Offer");
			priceExpression = xpath.compile("OfferListing/Price/Amount");
		} catch (final XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	private static Float extractAmazonPrice(final InputStream content) throws IOException {
		try {
			final DocumentBuilder db = dbf.newDocumentBuilder();
			final Document dom = db.parse(content);

			final NodeList evaluate = (NodeList) offersExpression.evaluate(dom, XPathConstants.NODESET);

			final SortedSet<Float> prices = new TreeSet<Float>();
			for (int i = 0; i < evaluate.getLength(); i++) {
				final Node offer = evaluate.item(i);

				// TODO: check CurrencyCode?
				final String priceString = priceExpression.evaluate(offer);
				prices.add(Integer.parseInt(priceString) / 100f);
			}

			// FIXME: better heuristic
			if (prices.size() > 0) {
				return prices.first();
			}
		} catch (final Exception e) {
			// TODO: log exception
			e.printStackTrace();
		} finally {
			content.close();
		}
		return null;
	}

	/**
	 * the client to use
	 */
	private HttpClient client = new DefaultHttpClient();

	private SignedRequestsHelper helper;

	@Override
	public Float updateItem(final Item item) {
		HttpEntity entity = null;
		try {
			final String url = this.getItemCheckUrl(item);
			final HttpGet get = new HttpGet(url);
			final HttpResponse response = this.client.execute(get);

			entity = response.getEntity();
			final InputStream content = entity.getContent();
			return extractAmazonPrice(content);
		} catch (final IOException e) {
			// TODO: log exception
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (final IOException e) {
				// TODO: handle exception
			}
		}
		return Float.NaN;
	}

	private String getItemCheckUrl(final Item item) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("MerchantId", "All");
		params.put("ItemId", item.getAsin());
		params.put("Condition", "New");
		params.put("ResponseGroup", "Offers");

		return this.helper.sign(Amazon.getEndPoint(item.getSite()), params);
	}

	/**
	 * @param helper the helper to set
	 */
	public void setHelper(final SignedRequestsHelper helper) {
		this.helper = helper;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(final HttpClient client) {
		this.client = client;
	}
}
