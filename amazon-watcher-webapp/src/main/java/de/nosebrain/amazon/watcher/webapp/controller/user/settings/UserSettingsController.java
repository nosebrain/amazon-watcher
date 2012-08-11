package de.nosebrain.amazon.watcher.webapp.controller.user.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.webapp.command.user.UserCommand;
import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
@Scope("request")
public class UserSettingsController {

	@Autowired
	private AmazonWatcherService service;

	/**
	 * shows the settings page
	 * @param command
	 * @param model
	 * @return the settings view
	 */
	@RequestMapping(Views.SETTINGS_PATH)
	public String settingsPage(final UserCommand command, final Model model) {
		command.setUser(this.service.getLoggedInUser());
		model.addAttribute(command);
		return Views.SETTINGS;
	}
}
