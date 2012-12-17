package de.nosebrain.amazon.watcher.webapp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AdminAmazonWatcherService;

@Controller
public class AdminPageController {

	@Autowired
	private AdminAmazonWatcherService service; // TODO: use user specific service

	@RequestMapping("/admin/items")
	public String listAllItems(final Model model) {
		model.addAttribute("items", this.service.getItems());
		return "admin/items";
	}

	@RequestMapping("/admin/users")
	public String listAllUsers(final Model model) {
		// model.addAttribute("users", service.getAllUsers());

		return "admin/users";
	}
}
