package de.nosebrain.amazon.watcher.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
@Controller
public class WatchedItemsController {
	
	@Autowired
	private AmazonWatcherService service;
	
	/**
	 * @param model the model to fill
	 * @return the homepage
	 */
	@RequestMapping("/")
	public String home(final Model model) {
		final List<Item> items = this.service.getItems();
		model.addAttribute("items", items);
		
		return "home";
	}
}
