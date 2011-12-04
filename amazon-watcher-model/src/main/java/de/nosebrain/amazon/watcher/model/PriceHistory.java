package de.nosebrain.amazon.watcher.model;

import java.util.Date;

/**
 * 
 * @author nosebrain
 */
public class PriceHistory {
	private Date date;
	private float value;

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	public float getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(final float value) {
		this.value = value;
	}

}
