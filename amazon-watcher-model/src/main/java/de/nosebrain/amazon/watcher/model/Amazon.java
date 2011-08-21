package de.nosebrain.amazon.watcher.model;

/**
 * 
 * @author nosebrain
 */
public enum Amazon {

	/**
	 * Amazon Germany
	 */
	DE("ecs.amazonaws.de"),
	
	/**
	 * Amazon USA
	 */
	USA("ecs.amazonaws.com");
	
	private final String url;

	private Amazon(String url) {
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
