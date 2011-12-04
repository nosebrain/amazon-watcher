package de.nosebrain.amazon.watcher.model;

import java.util.LinkedList;
import java.util.List;

import de.nosebrain.authentication.Authority;
import de.nosebrain.authentication.Role;

/**
 * 
 * @author nosebrain
 *
 */
public class User {
	private String name;
	private String mail;
	private String apiKey;

	private Role role = Role.DEFAULT;

	private UserSettings settings;

	private List<Authority> authorities;

	/**
	 * default constructor
	 */
	public User() {

	}

	/**
	 * @param name
	 */
	public User(final String name) {
		this.setName(name);
	}

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
	 * @return the mail
	 */
	public String getMail() {
		return this.mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}

	/**
	 * @return the settings
	 */
	public UserSettings getSettings() {
		return this.settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(final UserSettings settings) {
		this.settings = settings;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return this.apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(final Role role) {
		this.role = role;
	}

	/**
	 * @return the authorities
	 */
	public List<Authority> getAuthorities() {
		if (this.authorities == null) {
			this.authorities = new LinkedList<Authority>();
		}

		return this.authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(final List<Authority> authorities) {
		this.authorities = authorities;
	}
}
