package de.nosebrain.amazon.watcher.webapp.command.user;

import de.nosebrain.amazon.watcher.model.User;

/**
 * 
 * @author nosebrain
 */
public class UserCommand {
	private User user = new User();

	private String password;

	private String retype;

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the retype
	 */
	public String getRetype() {
		return this.retype;
	}

	/**
	 * @param retype the retype to set
	 */
	public void setRetype(final String retype) {
		this.retype = retype;
	}
}
