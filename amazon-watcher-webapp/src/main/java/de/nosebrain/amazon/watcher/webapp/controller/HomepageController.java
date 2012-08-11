package de.nosebrain.amazon.watcher.webapp.controller;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.ItemViewMode;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.webapp.services.UpdaterService;
import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
@Scope("request")
public class HomepageController {

	@Autowired
	private AmazonWatcherService service;

	@Autowired
	private UpdaterService updaterService;

	/**
	 * @param viewMode the view mode
	 * @param model the model to fill
	 * @param principal the logged in user
	 * @return the homepage
	 */
	@RequestMapping(value = {"/", "/index"})
	public String home(@RequestParam(value = "viewmode", required = false) final ItemViewMode viewMode, final Model model, final Authentication principal) {
		if (present(principal)) {
			final List<Observation> observations = this.service.getObservations();
			model.addAttribute("observations", observations);
			// TODO: inform about next update
			model.addAttribute("lastUpdateDate", this.updaterService.getLastUpdateDate());
			model.addAttribute("requestedViewMode", viewMode);
		}

		return Views.HOME;
	}
}
