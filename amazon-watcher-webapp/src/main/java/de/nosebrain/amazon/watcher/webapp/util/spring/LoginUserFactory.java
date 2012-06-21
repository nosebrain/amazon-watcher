package de.nosebrain.amazon.watcher.webapp.util.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.webapp.util.spring.security.UserAdapter;

/**
 * 
 * @author nosebrain
 */
public class LoginUserFactory implements FactoryBean<User> {

	@Override
	public User getObject() throws Exception {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			final Object details = authentication.getPrincipal();
			if (details instanceof UserAdapter) {
				return ((UserAdapter) details).getUser();
			}
		}

		return new User();
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
