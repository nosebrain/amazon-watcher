package de.nosebrain.amazon.watcher.updater;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.services.Updater;
import de.nosebrain.amazon.watcher.updater.util.SignedRequestsHelper;

/**
 * 
 * @author nosebrain
 */
public class ProductAdvertisingAPIUpdater implements Updater {

	/**
	 * the client to use
	 */
	private final HttpClient client = new DefaultHttpClient();
	
	private SignedRequestsHelper helper;
	
	@Override
	public void updateItem(final Item item) {
		try {
			final HttpGet get = new HttpGet(this.getItemCheckURI(item.getAsin()));
			final HttpResponse response = this.client.execute(get);
		
			final InputStream content = response.getEntity().getContent();
			
			
		} catch (final IOException e) {
			// TODO: log exception
		}
	}
	
	private String getItemCheckURI(final String asin) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", asin);
		params.put("ResponseGroup", "Offers");
		
		return this.helper.sign(params);
	}

	public static void main(String[] args) {
		final ProductAdvertisingAPIUpdater api = new ProductAdvertisingAPIUpdater();
		final SignedRequestsHelper helper = new SignedRequestsHelper();
		helper.setEndpoint("ecs.amazonaws.de");
		helper.setAwsAccessKeyId("AKIAJXA6G24447ZIQJ3Q");
		helper.setAwsSecretKey("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");
		helper.init();
		
		api.setHelper(helper);
		
		final Item item = new Item();
		item.setAsin("B00411RW82");
		api.updateItem(item);
	}

	/**
	 * @param helper the helper to set
	 */
	public void setHelper(SignedRequestsHelper helper) {
		this.helper = helper;
	}
}
