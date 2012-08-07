package de.nosebrain.amazon.watcher.database.util;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public class ItemParam extends UserAwareParam {

	private final Item item;
	private float price;
	private int id;

	/**
	 * default constructor
	 */
	public ItemParam() {
		this.item = null;
	}

	/**
	 * constructor with item param
	 * @param item
	 */
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
