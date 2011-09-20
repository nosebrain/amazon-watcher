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
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
	
}
