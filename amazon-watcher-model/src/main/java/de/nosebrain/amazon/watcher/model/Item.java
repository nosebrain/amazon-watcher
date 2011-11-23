package de.nosebrain.amazon.watcher.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author nosebrain
 */
public class Item {

	private String asin;
	private Amazon site;

	private List<PriceHistory> priceHistories;

	/**
	 * @return the asin
	 */
	public String getAsin() {
		return this.asin;
	}

	/**
	 * @param asin the asin to set
	 */
	public void setAsin(final String asin) {
		this.asin = asin;
	}

	/**
	 * @return the site
	 */
	public Amazon getSite() {
		return this.site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(final Amazon site) {
		this.site = site;
	}

	/**
	 * @return the priceHistories
	 */
	public List<PriceHistory> getPriceHistories() {
		if (this.priceHistories == null) {
			this.priceHistories = new LinkedList<PriceHistory>();
		}
		return this.priceHistories;
	}

	/**
	 * @param priceHistories the priceHistories to set
	 */
	public void setPriceHistories(final List<PriceHistory> priceHistories) {
		this.priceHistories = priceHistories;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.asin == null ? 0 : this.asin.hashCode());
		result = prime * result + (this.site == null ? 0 : this.site.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Item other = (Item) obj;
		if (this.asin == null) {
			if (other.asin != null) {
				return false;
			}
		} else if (!this.asin.equals(other.asin)) {
			return false;
		}
		if (this.site != other.site) {
			return false;
		}
		return true;
	}
}
