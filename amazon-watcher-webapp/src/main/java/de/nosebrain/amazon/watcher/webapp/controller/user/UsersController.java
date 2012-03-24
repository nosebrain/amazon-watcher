package de.nosebrain.amazon.watcher.webapp.controller.user;

import static de.nosebrain.util.ValidationUtils.present;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import de.nosebrain.amazon.watcher.webapp.validation.UserValidator;
import de.nosebrain.authentication.Role;

/**
 * 
 * @author nosebrain
 */
@Controller
public class UsersController {

	@Autowired
	private AmazonWatcherService service;

	@Autowired
	private AdminAmazonWatcherService adminService;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.setValidator(new UserValidator());
	}

	@RequestMapping("/login")
	public String login() {
		return "services/login";
	}

	@RequestMapping("/doLogin")
	public String callLoginService(@RequestParam("serviceId") final String loginServiceId) {
		return null;
	}

	@RequestMapping("/register")
	public String regiser(@Valid final User user, final BindingResult result, final Model model) {
		model.addAttribute(user);
		return "services/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerNewUser(@Valid final User user, final BindingResult result, @RequestParam("password") final String password, @RequestParam("retype") final String retype, final Model model) {
		final String userName = user.getName();
		final User userInDd = this.adminService.getUserByName(userName);
		if (present(userInDd)) {
			result.rejectValue("name", "user.name.invalid.duplicate");
		}

		/*
		 * if there are errors show register form
		 */
		if (result.hasErrors()) {
			return "services/register";
		}

		user.setRole(Role.DEFAULT);
		this.adminService.createUser(user);

		return "services/welcome";
	}

	@RequestMapping("/settings")
	public String settingsPage(final Model model) {
		model.addAttribute(this.service.getLoggedInUser());
		return "services/settings";
	}
}
