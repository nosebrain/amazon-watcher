package de.nosebrain.amazon.watcher.webapp.util;

import java.util.Map;

import de.nosebrain.authentication.AuthMethod;


/**
 * 
 * @author nosebrain
 */
public class AuthService {

	private String name;
	private AuthMethod method;

	private String refererParameter;
	private Map<String, String> systemParameters;
	private String url; // TODO: rename to end point

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
}
