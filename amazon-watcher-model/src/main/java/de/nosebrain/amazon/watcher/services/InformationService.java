package de.nosebrain.amazon.watcher.services;

import java.util.List;

import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
public interface InformationService {

	/**
	 * @param items inform the user about the 
	 */
	public void inform(List<Item> items);
}
