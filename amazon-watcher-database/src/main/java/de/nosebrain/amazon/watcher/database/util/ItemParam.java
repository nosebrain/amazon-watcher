package de.nosebrain.amazon.watcher.database.util;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 *
 */
public class ItemParam {

	private final Item item;
	private float price;
	private int id;
	private String userName;

	public ItemParam() {
		this.item = null;
	}

	public ItemParam(final Item item) {
		this.item = item;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return this.item;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(final float price) {
		this.price = price;
	}

}
