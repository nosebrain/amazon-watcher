package de.nosebrain.amazon.watcher.webapp.util.spring.security;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.authentication.AuthUtils;
import de.nosebrain.authentication.PasswordAuthority;
import de.nosebrain.authentication.Role;

/**
 * 
 * @author nosebrain
 */
public class UserAdapter implements UserDetails {
	private static final long serialVersionUID = -7654564291837101718L;

	private static final String USER_AUTHORITY = "USER";
	private static final String ADMIN_AUTHORITY = "ADMIN";


	private final User user;

	/**
	 * @param user the user to adapt
	 */
	public UserAdapter(final User user) {
		this.user = user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		final Collection<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl(USER_AUTHORITY));

		if (Role.ADMIN.equals(this.user.getRole())) {
			authorities.add(new GrantedAuthorityImpl(ADMIN_AUTHORITY));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return AuthUtils.getPassword(this.user.getAuthorities());
	}

	@Override
	public String getUsername() {
		return this.user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return AuthUtils.containsAuthority(this.user.getAuthorities(), PasswordAuthority.class);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}
}
