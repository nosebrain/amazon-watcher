package de.nosebrain.amazon.watcher.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.webapp.services.UpdaterService;
import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
public class HomepageController {

	@Autowired
	private AmazonWatcherService service;

	@Autowired
	private UpdaterService updaterService;

	/**
	 * @param model the model to fill
	 * @return the homepage
	 */
	@RequestMapping(value = {"/", "/index"})
	public String home(final Model model) {
		final List<Observation> observations = this.service.getObservations();
		model.addAttribute("observations", observations);

		// TODO: inform about next update
		model.addAttribute("lastUpdateDate", this.updaterService.getLastUpdateDate());

		return Views.HOME;
	}
}
