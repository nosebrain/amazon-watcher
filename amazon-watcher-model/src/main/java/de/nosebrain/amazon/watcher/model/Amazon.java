package de.nosebrain.amazon.watcher.model;

import java.util.Locale;

/**
 * 
 * @author nosebrain
 */
public enum Amazon {

	/**
	 * Amazon Germany
	 */
	DE("de", "€", Locale.GERMAN),

	/**
	 * Amazon USA
	 */
	US("com", "$", Locale.US);

	private final String tld;
	private final String currency;
	private final Locale numberFormatLocale;

	private Amazon(final String tld, final String currency, final Locale numberFormatLocale) {
		this.tld = tld;
		this.currency = currency;
		this.numberFormatLocale = numberFormatLocale;
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
	 * @return the numberFormatLocale
	 */
	public Locale getNumberFormatLocale() {
		return this.numberFormatLocale;
	}

	/**
	 * @param site
	 * @return the amazon aws site for the amazon site
	 */
	public static String getEndPoint(final Amazon site) {
		return "ecs.amazonaws." + site.getTLD();
	}
}
