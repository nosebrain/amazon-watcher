package de.nosebrain.amazon.watcher.webapp.controller.user.settings;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.webapp.command.user.UserCommand;
import de.nosebrain.amazon.watcher.webapp.util.ControllerUtils;

/**
 * 
 * @author nosebrain
 */
@Controller
@Scope("request")
public class UserSettingsController {

	// TODO: move
	public static final String SETTINGS = "services/settings";
	public static final String SETTINGS_PATH = "/settings";

	@Autowired
	private AmazonWatcherService service;

	/**
	 * shows the settings page
	 * @param command
	 * @param model
	 * @param session the session
	 * @return the settings view
	 */
	@RequestMapping(SETTINGS_PATH)
	public String settingsPage(final UserCommand command, final Model model, final HttpSession session) {
		command.setUser(this.service.getLoggedInUser());
		model.addAttribute(command);

		ControllerUtils.copyMessage(model, session);
		return SETTINGS;
	}
}
