package de.nosebrain.amazon.watcher.database.util;

/**
 * 
 * @author nosebrain
 */
public class PriceParam {
	private float price;
	private String asin;
	
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
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
	
	
}
