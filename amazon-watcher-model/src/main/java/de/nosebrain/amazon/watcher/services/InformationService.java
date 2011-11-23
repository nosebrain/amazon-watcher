package de.nosebrain.amazon.watcher.services;

import java.util.List;

import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.User;

/**
 * 
 * @author nosebrain
 */
public interface InformationService {

	/**
	 * @param observations inform the user about the changes
	 */
	public void inform(final User user, final List<Observation> observations);
}
