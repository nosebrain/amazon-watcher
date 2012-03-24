package de.nosebrain.amazon.watcher.model;

/**
 * 
 * @author nosebrain
 */
public enum Amazon {

	/**
	 * Amazon Germany
	 */
	DE("de", "€"),

	/**
	 * Amazon USA
	 */
	US("com", "$");

	private final String tld;
	private final String currency;

	private Amazon(final String tld, final String currency) {
		this.tld = tld;
		this.currency = currency;
	}

	/**
	 * @return the tld
	 */
	public String getTLD() {
		return this.tld;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return this.currency;
	}


	/**
	 * @param site
	 * @return the amazon aws site for the amazon site
	 */
	public static String getEndPoint(final Amazon site) {
		return "ecs.amazonaws." + site.getTLD();
	}
}
