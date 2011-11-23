package de.nosebrain.amazon.watcher.database.util;

import de.nosebrain.amazon.watcher.model.Observation;

public class ObservationParam {
	private Observation observation;
	private String userName;
	private int itemId;

	/**
	 * @return the observation
	 */
	public Observation getObservation() {
		return this.observation;
	}

	/**
	 * @param observation the observation to set
	 */
	public void setObservation(final Observation observation) {
		this.observation = observation;
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
	 * @return the itemId
	 */
	public int getItemId() {
		return this.itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(final int itemId) {
		this.itemId = itemId;
	}

}
