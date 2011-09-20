package de.nosebrain.amazon.watcher.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.webapp.services.UpdaterService;
import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
public class WatchedItemsController {
	
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
		final List<Item> items = this.service.getItems();
		model.addAttribute("items", items);
		model.addAttribute("lastUpdateDate", this.updaterService.getLastUpdateDate());
		
		return Views.HOME;
	}
	
	/**
	 * @return redirect to homepage
	 */
	@RequestMapping("/updateItems")
	public String updateItems() {
		this.updaterService.updateItems();
		return Views.HOME_REDIRECT;
	}
}
