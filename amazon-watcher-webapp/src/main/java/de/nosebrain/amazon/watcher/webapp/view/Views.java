package de.nosebrain.amazon.watcher.webapp.view;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public class Views {

	/**
	 * redirect prefix
	 */
	public static final String REDIRECT = "redirect:";

	/**
	 * redirect to home
	 */
	public static final String HOME_REDIRECT = REDIRECT + "/";

	/**
	 * home view
	 */
	public static final String HOME = "home";

	/**
	 * observation view
	 */
	public static final String OBSERVATIONS = "observations";

	public static final String OBSERVATION_EDIT = OBSERVATIONS + "/edit";

	public static final String ERROR_VIEW = "errors/errors";

	public static final String getObservationEditUrl(final Item item) {
		return REDIRECT + OBSERVATIONS + "/" + item.getSite() + "_" + item.getAsin() + "/edit";
	}

	public static final String SESSION_MESSAGE = "message";

	public static final String SESSION_MESSAGE_PARAMS = "messageParams";

	// TODO: move
	public static final String SETTINGS = "services/settings";

	public static final String SETTINGS_PATH = "/settings";

	public static final String SETTINGS_REDIRECT = REDIRECT + SETTINGS_PATH;

	public static final String INFO_SERVICES_PATH = SETTINGS_PATH + "/infoServices";
}
