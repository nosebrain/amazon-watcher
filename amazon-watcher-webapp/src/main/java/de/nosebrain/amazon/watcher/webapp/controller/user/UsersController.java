package de.nosebrain.amazon.watcher.webapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.User;

@Controller
public class UsersController {

	@Autowired
	private AmazonWatcherService service;

	@RequestMapping("/login")
	public String login() {
		return "services/login";
	}

	@RequestMapping("/register")
	public String regiser(final User user, final Model model) {
		model.addAttribute(user);
		return "services/register";
	}

	@RequestMapping("/settings")
	public String settingsPage(final Model model) {
		model.addAttribute(this.service.getLoggedInUser());
		return "services/settings";
	}
}
