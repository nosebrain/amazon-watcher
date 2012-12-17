package de.nosebrain.amazon.watcher.webapp.controller.user.settings;

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
public class UserSettingsController extends AbstractSettingsController {

	public static final String ACTIVE_TAB = "active_tab";

	/**
	 * shows the settings page
	 * @param command
	 * @param model
	 * @return the settings view
	 */
	@RequestMapping(Views.SETTINGS_PATH)
	public String settingsPage(final AmazonWatcherService service, final UserCommand command, final Model model) {
		return this.internalRenderSettingsView(service, command, model);
	}
}
