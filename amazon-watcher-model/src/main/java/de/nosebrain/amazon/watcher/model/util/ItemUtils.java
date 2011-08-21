package de.nosebrain.amazon.watcher.model.util;

import java.net.URL;
import java.util.StringTokenizer;

/**
 * 
 * @author nosebrain
 */
public final class ItemUtils {
	
	private ItemUtils() {}
	
	/**
	 * extracts the ASIN from the url
	 * 
	 * @param url the url to use
	 * @return the ASIN
	 */
	public static String extractASIN(final URL url) {
		final String path = url.getPath();
		final StringTokenizer tokenizer = new StringTokenizer(path, "/");
		if (tokenizer.hasMoreTokens()) {
			final String first = tokenizer.nextToken();
			if ("gp".equals(first)) {
				if (tokenizer.hasMoreTokens()) {
					final String product = tokenizer.nextToken();
					if ("product".equals(product) && tokenizer.hasMoreTokens()) {
						final String asin = tokenizer.nextToken();
						if (asin.length() == 10) {
							return asin;
						}
					}					
				}
			} else {
				if (tokenizer.hasMoreTokens()) {
					final String dp = tokenizer.nextToken();
					if ("dp".equals(dp) && tokenizer.hasMoreElements()) {
						final String asin = tokenizer.nextToken();
						
						if (asin.length() == 10) {
							return asin;
						}
					}
				}
			}
		}
		
		// TODO: log
		return "";
	}
}
