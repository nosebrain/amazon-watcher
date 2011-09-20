package de.nosebrain.amazon.watcher.model;

/**
 * 
 * @author nosebrain
 */
public enum Amazon {

	/**
	 * Amazon Germany
	 */
	DE("ecs.amazonaws.de", "€"),
	
	/**
	 * Amazon USA
	 */
	USA("ecs.amazonaws.com", "$");
	
	private final String url;
	private final String currency;

	private Amazon(final String url, final String currency) {
		this.url = url;
		this.currency = currency;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return this.currency;
	}
}
