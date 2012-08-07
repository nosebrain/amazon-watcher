package de.nosebrain.amazon.watcher.webapp.controller.user;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.LinkedList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;
import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.webapp.command.user.UserCommand;
import de.nosebrain.amazon.watcher.webapp.validation.UserCommandValidator;
import de.nosebrain.authentication.Authority;
import de.nosebrain.authentication.PasswordAuthority;
import de.nosebrain.authentication.Role;
import de.nosebrain.util.StringUtils;

/**
 * 
 * @author nosebrain
 */
@Controller
@Scope("request")
public class UsersController {

	private static final String WELCOME_VIEW = "services/welcome";
	private static final String LOGIN_VIEW = "services/login";
	private static final String REGISTER_VIEW = "services/register";

	@Autowired
	private AmazonWatcherService service;

	@Autowired
	private AdminAmazonWatcherService adminService;

	/**
	 * inits the binder
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.setValidator(new UserCommandValidator());
	}

	/**
	 * for the login view
	 * @param model
	 * @param session
	 * @return the login view
	 */
	@RequestMapping("/login")
	public String login(final Model model, final HttpSession session) {
		final Exception lastException = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (present(lastException)) {
			model.addAttribute("lastException", lastException.getClass().getSimpleName());
			session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
		}

		return LOGIN_VIEW;
	}

	@RequestMapping("/doLogin")
	public String callLoginService(@RequestParam("serviceId") final String loginServiceId) {
		// TODO: implement me
		return null;
	}

	/**
	 * renders the register view
	 * 
	 * @param userCommand	the user command
	 * @param result		the binding result
	 * @param model			the model
	 * @return the register view
	 */
	@RequestMapping("/register")
	public String regiser(@Valid final UserCommand userCommand, final BindingResult result, final Model model) {
		model.addAttribute(userCommand);
		return REGISTER_VIEW;
	}


	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerNewUser(@Valid final UserCommand command, final BindingResult result, final Model model) {
		final User user = command.getUser();
		final String userName = user.getName();
		final User userInDd = this.adminService.getUserByName(userName);
		if (present(userInDd)) {
			result.rejectValue("user.name", "user.name.invalid.duplicate");
		}

		/*
		 * if there are errors show register form
		 */
		if (result.hasErrors()) {
			return REGISTER_VIEW;
		}

		user.setRole(Role.DEFAULT);
		final LinkedList<Authority> authorities = new LinkedList<Authority>();
		user.setAuthorities(authorities);
		final String password = command.getPassword();
		if (present(password)) {
			final PasswordAuthority passwordAuth = new PasswordAuthority();
			passwordAuth.setPassword(StringUtils.md5(password));
			authorities.add(passwordAuth);
		}

		this.adminService.createUser(user);

		return WELCOME_VIEW;
	}
}
