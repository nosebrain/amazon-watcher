package de.nosebrain.amazon.watcher.model;

/**
 * condition of an {@link ItemCondition}
 * 
 * @author nosebrain
 */
public enum ItemCondition {
	/**
	 * new and used
	 */
	ALL,

	/**
	 * only new items
	 */
	NEW,

	/**
	 * only used items
	 */
	USED,

	/**
	 * only refurbished items between new and used
	 */
	REFURBISHED;
}
