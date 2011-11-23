package de.nosebrain.amazon.watcher.webapp.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.model.User;

@Controller
public class UsersController {

	@RequestMapping("/login")
	public String login() {
		return "services/login";
	}

	@RequestMapping("/register")
	public String regiser(final User user, final Model model) {
		model.addAttribute(user);
		return "services/register";
	}
}
