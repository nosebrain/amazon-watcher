package de.nosebrain.amazon.watcher.services;

import java.util.List;
import java.util.Locale;

import de.nosebrain.amazon.watcher.model.Observation;

/**
 * 
 * @author nosebrain
 */
public interface InformationService {

	/**
	 * @param observations inform the user about the changes
	 */
	public void inform(final List<Observation> observations, final Locale language) throws Exception;

	public void testService(final Locale language) throws Exception;
}
