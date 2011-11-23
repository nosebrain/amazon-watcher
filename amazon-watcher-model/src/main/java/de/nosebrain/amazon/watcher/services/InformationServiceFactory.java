package de.nosebrain.amazon.watcher.services;

/**
 * 
 * @author dzo
 *
 */
public interface InformationServiceFactory {

	/**
	 * 
	 * @param settings
	 * @return
	 */
	public InformationService createInformationService(final String settings);
}
