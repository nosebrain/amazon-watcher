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
	 * @return the information service
	 */
	public InformationService createInformationService(final String settings);
}
