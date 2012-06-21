package de.nosebrain.amazon.watcher.webapp.view;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;

/**
 * 
 * @author nosebrain
 */
public class Views {

	public static final String REDIRECT = "redirect:";

	/**
	 * redirect to home
	 */
	public static final String HOME_REDIRECT = REDIRECT + "/";

	/**
	 * home view
	 */
	public static final String HOME = "home";

	public static final String OBSERVATIONS = "observations";

	public static final String OBSERVATION_EDIT = OBSERVATIONS + "/edit";

	public static final String ERROR_VIEW = "errors/errors";

	public static final String getObservationEditUrl(final Item item) {
		return REDIRECT + OBSERVATIONS + "/" + ItemUtils.generateUrlForItem(item) + "/edit";
	}
}
