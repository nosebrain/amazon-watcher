package de.nosebrain.amazon.watcher.model;

import java.util.Date;

/**
 * 
 * @author nosebrain
 *
 */
public class Observation {
	private Item item;

	private String name;
	private Float limit;
	private ObservationMode mode;
	private ItemCondition itemCondition;
	private Seller seller;
	private Date date;
	private Date changeDate;

	/**
	 * @return the item
	 */
	public Item getItem() {
		return this.item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(final Item item) {
		this.item = item;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the limit
	 */
	public Float getLimit() {
		return this.limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(final Float limit) {
		this.limit = limit;
	}

	/**
	 * @return the mode
	 */
	public ObservationMode getMode() {
		return this.mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(final ObservationMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the itemCondition
	 */
	public ItemCondition getItemCondition() {
		return this.itemCondition;
	}

	/**
	 * @param itemCondition the itemCondition to set
	 */
	public void setItemCondition(final ItemCondition itemCondition) {
		this.itemCondition = itemCondition;
	}

	/**
	 * @return the seller
	 */
	public Seller getSeller() {
		return this.seller;
	}

	/**
	 * @param seller the seller to set
	 */
	public void setSeller(final Seller seller) {
		this.seller = seller;
	}

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
	 * @return the changeDate
	 */
	public Date getChangeDate() {
		return this.changeDate;
	}

	/**
	 * @param changeDate the changeDate to set
	 */
	public void setChangeDate(final Date changeDate) {
		this.changeDate = changeDate;
	}


}
