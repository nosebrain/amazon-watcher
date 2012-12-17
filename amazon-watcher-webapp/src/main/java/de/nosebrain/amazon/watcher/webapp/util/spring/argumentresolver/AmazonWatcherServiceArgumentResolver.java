package de.nosebrain.amazon.watcher.webapp.util.spring.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.webapp.util.spring.security.UserAdapter;

public abstract class AmazonWatcherServiceArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return AmazonWatcherService.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		final AmazonWatcherService service = this.createAmazonWatcherService();
		service.setLoggedinUser(this.getLoggedinUser());
		return service;
	}
	
	private User getLoggedinUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			final Object details = authentication.getPrincipal();
			if (details instanceof UserAdapter) {
				return ((UserAdapter) details).getUser();
			}
		}

		return new User();
	}

	protected abstract AmazonWatcherService createAmazonWatcherService();

}
