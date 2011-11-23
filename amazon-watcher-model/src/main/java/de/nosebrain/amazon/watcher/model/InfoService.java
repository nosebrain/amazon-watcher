package de.nosebrain.amazon.watcher.model;

import de.nosebrain.util.StringUtils;

/**
 * 
 * @author nosebrain
 *
 */
public class InfoService {
	private String infoServiceKey;
	private String settings;
	private String hash;

	/**
	 * @return the infoServiceKey
	 */
	public String getInfoServiceKey() {
		return this.infoServiceKey;
	}

	/**
	 * @param infoServiceKey the infoServiceKey to set
	 */
	public void setInfoServiceKey(final String infoServiceKey) {
		this.infoServiceKey = infoServiceKey;
	}

	/**
	 * @return the settings
	 */
	public String getSettings() {
		return this.settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(final String settings) {
		this.settings = settings;
	}

	/**
	 * recalculates the hash
	 */
	public void recalculateHash() {
		this.hash = StringUtils.md5(this.infoServiceKey + this.settings);
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return this.hash;
	}
}
