package de.nosebrain.amazon.watcher.updater;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.nosebrain.amazon.watcher.services.Updater;
import de.nosebrain.amazon.watcher.updater.util.SignedRequestsHelper;

/**
 * 
 * @author nosebrain
 */
public class ProductAdvertisingAPIUpdater implements Updater {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	private static final XPathExpression offersExpression;
	private static final XPathExpression merchantExpression;
	private static final XPathExpression priceExpression;
	
	// TODO: amazon.com = amazon.de?
	private static final String AMAZON_ID = "A3JWKAKR8XB7XF";
	
	static {
		final XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			offersExpression = xpath.compile("/ItemLookupResponse/Items/Item/Offers/Offer");
			merchantExpression = xpath.compile("Merchant/MerchantId");
			priceExpression = xpath.compile("OfferListing/Price/Amount");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * the client to use
	 */
	private HttpClient client = new DefaultHttpClient();
	
	private SignedRequestsHelper helper;
	
	@Override
	public Float updateItem(final String asin) {
		try {
			final String string = this.getItemCheckUrl(asin);
			final HttpGet get = new HttpGet(string);
			final HttpResponse response = this.client.execute(get);
		
			final InputStream content = response.getEntity().getContent();
			return this.extractAmazonPrice(content);
		} catch (final IOException e) {
			// TODO: log exception
		}
		return Float.NaN;
	}
	
	private Float extractAmazonPrice(final InputStream content) throws IOException {
		try {
			final DocumentBuilder db = dbf.newDocumentBuilder();
			final Document dom = db.parse(content);
			
			final NodeList evaluate = (NodeList) offersExpression.evaluate(dom, XPathConstants.NODESET);
			
			for (int i = 0; i < evaluate.getLength(); i++) {
				final Node offer = evaluate.item(i);
				
				// TODO: check CurrencyCode?
				final String merchantId = merchantExpression.evaluate(offer);
				if (AMAZON_ID.equals(merchantId)) {
					final String priceString = priceExpression.evaluate(offer);
					return Integer.parseInt(priceString) / 100f;
				}
			}
		} catch (final Exception e) {
			// TODO: log exception
		} finally {
			content.close();
		}
		return null;
	}

	private String getItemCheckUrl(final String asin) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", asin);
		params.put("ResponseGroup", "Offers");
		
		return this.helper.sign(params);
	}
	
	/**
	 * @param helper the helper to set
	 */
	public void setHelper(SignedRequestsHelper helper) {
		this.helper = helper;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}
}
