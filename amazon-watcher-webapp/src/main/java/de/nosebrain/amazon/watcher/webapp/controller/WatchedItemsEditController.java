package de.nosebrain.amazon.watcher.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 */
@Controller
public class WatchedItemsEditController {
	
	@Autowired
	private AmazonWatcherService service;	
	
	@RequestMapping(value="/items/edit", method = RequestMethod.GET)
	public String itemEditForm(final Model model) {
		model.addAttribute(new Item());
		return "items/edit";
	}
	
	@RequestMapping(value="/items/edit", method = RequestMethod.POST)
	public String saveItem(final Item item) {
		this.service.watchItem(item);
		return "todo";
	}
	
	
}
