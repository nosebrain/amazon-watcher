package de.nosebrain.amazon.watcher.model;

import java.net.URL;
import java.util.Date;

/**
 * 
 * @author nosebrain
 */
public class Item {
	
	private String asin;
	
	private String name;
	
	private URL url;
	
	private Float limit;
	
	private WatchMode mode;
	
	private Float lastPrice;
	
	private Float currentPrice;
	
	private Date date;
	
	private Date changeDate;

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @return the mode
	 */
	public WatchMode getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(WatchMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the lastPrice
	 */
	public Float getLastPrice() {
		return lastPrice;
	}

	/**
	 * @param lastPrice the lastPrice to set
	 */
	public void setLastPrice(Float lastPrice) {
		this.lastPrice = lastPrice;
	}

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
	 * @return the changeDate
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate the changeDate to set
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the limit
	 */
	public Float getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Float limit) {
		this.limit = limit;
	}

	/**
	 * @return the asin
	 */
	public String getAsin() {
		return asin;
	}

	/**
	 * @param asin the asin to set
	 */
	public void setAsin(String asin) {
		this.asin = asin;
	}

	/**
	 * @return the currentPrice
	 */
	public Float getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(Float currentPrice) {
		this.currentPrice = currentPrice;
	}
}
