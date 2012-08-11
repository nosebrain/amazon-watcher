package de.nosebrain.amazon.watcher.webapp.util.spring.security;

import de.nosebrain.amazon.watcher.model.User;

/**
 * adapter that uses the api key as password
 * 
 * @author nosebrain
 */
public class UserAPIAdapter extends UserAdapter {
	private static final long serialVersionUID = 7454788105170096207L;

	/**
	 * @param user the user to adapt
	 */
	public UserAPIAdapter(final User user) {
		super(user);
	}

	/**
	 * @return the api key as password
	 */
	@Override
	public String getPassword() {
		return this.getUser().getApiKey();
	}
}
