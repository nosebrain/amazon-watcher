package de.nosebrain.amazon.watcher.model;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author nosebrain
 */
public class Item {
	
	private String asin;

	private URL url;
	private List<PriceHistory> priceHistories;
	
	private String name;
	private Float limit;
	private WatchMode mode;
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
	 * @return the priceHistories
	 */
	public List<PriceHistory> getPriceHistories() {
		if (this.priceHistories == null) {
			this.priceHistories = new LinkedList<PriceHistory>();
		}
		return priceHistories;
	}

	/**
	 * @param priceHistories the priceHistories to set
	 */
	public void setPriceHistories(List<PriceHistory> priceHistories) {
		this.priceHistories = priceHistories;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asin == null) ? 0 : asin.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (asin == null) {
			if (other.asin != null) {
				return false;
			}
		} else if (!asin.equals(other.asin)) {
			return false;
		}
		return true;
	}
}
