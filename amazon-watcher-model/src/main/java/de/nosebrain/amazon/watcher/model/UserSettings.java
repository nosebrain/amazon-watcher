package de.nosebrain.amazon.watcher.model;

import java.util.LinkedList;
import java.util.List;

import de.nosebrain.Language;

/**
 * 
 * @author nosebrain
 *
 */
public class UserSettings {

	public static UserSettings getDefaultSettings() {
		final UserSettings settings = new UserSettings();
		settings.setMinDelta(0.25f);
		settings.setLanguage(Language.DE);
		return settings;
	}


	private float minDelta;
	private List<InfoService> infoServices;
	private Language language;

	/**
	 * @return the minDelta
	 */
	public Float getMinDelta() {
		return this.minDelta;
	}

	/**
	 * @param minDelta the minDelta to set
	 */
	public void setMinDelta(final Float minDelta) {
		this.minDelta = minDelta;
	}

	/**
	 * @return the infoServices
	 */
	public List<InfoService> getInfoServices() {
		if (this.infoServices == null) {
			this.infoServices = new LinkedList<InfoService>();
		}
		return this.infoServices;
	}

	/**
	 * @param infoServices the infoServices to set
	 */
	public void setInfoServices(final List<InfoService> infoServices) {
		this.infoServices = infoServices;
	}

	/**
	 * @return the language
	 */
	public Language getLanguage() {
		return this.language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(final Language language) {
		this.language = language;
	}

}
