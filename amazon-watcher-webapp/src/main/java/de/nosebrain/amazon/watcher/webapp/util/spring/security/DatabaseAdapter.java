package de.nosebrain.amazon.watcher.webapp.util.spring.security;

import static de.nosebrain.util.ValidationUtils.present;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;
import de.nosebrain.amazon.watcher.model.User;

/**
 * adapter of {@link AdminAmazonWatcherService} for {@link UserDetailsService}
 * 
 * @author nosebrain
 */
public class DatabaseAdapter implements UserDetailsService {

	private AdminAmazonWatcherService adminService;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
		final User userInDb = this.adminService.getUserByName(username);
		if (!present(userInDb)) {
			throw new UsernameNotFoundException("user " + username + " not found");
		}

		return new UserAdapter(userInDb);
	}

	/**
	 * @param adminService the adminService to set
	 */
	public void setAdminService(final AdminAmazonWatcherService adminService) {
		this.adminService = adminService;
	}
}
