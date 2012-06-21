package de.nosebrain.amazon.watcher.database.util;

import de.nosebrain.authentication.Authority;

/**
 * 
 * @author nosebrain
 */
public class AuthorityParam {

	private Authority authority;
	private String userName;

	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return this.authority;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(final Authority authority) {
		this.authority = authority;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}
}
