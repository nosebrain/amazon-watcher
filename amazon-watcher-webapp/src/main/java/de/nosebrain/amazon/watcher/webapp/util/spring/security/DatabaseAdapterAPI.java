package de.nosebrain.amazon.watcher.webapp.util.spring.security;

import de.nosebrain.amazon.watcher.model.User;

/**
 * special {@link DatabaseAdapter} for the api key
 * 
 * @author nosebrain
 */
public class DatabaseAdapterAPI extends DatabaseAdapter {

	/**
	 * @return {@link UserAPIAdapter} to use the password as api key
	 */
	@Override
	protected UserAdapter createUserAdapter(final User userInDb) {
		return new UserAPIAdapter(userInDb);
	}
}
