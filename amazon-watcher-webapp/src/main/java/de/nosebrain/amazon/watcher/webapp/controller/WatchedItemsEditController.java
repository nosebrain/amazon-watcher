package de.nosebrain.amazon.watcher.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
public class WatchedItemsEditController {
	@Autowired
	private AmazonWatcherService service;	
	
	/**
	 * @param model the model to fill
	 * @return the item form view
	 */
	@RequestMapping(value="/items/edit", method = RequestMethod.GET)
	public String itemEditForm(final Model model) {
		model.addAttribute(new Item());
		return "items/edit";
	}
	
	/**
	 * @param item the item to create
	 * @return the view to render
	 */
	@RequestMapping(value="/items/edit", method = RequestMethod.POST)
	public String saveItem(final Item item) {
		// TODO: add checks
		// mode == LIMIT limit set not negative not 0
		this.service.watchItem(item);
		
		return Views.HOME_REDIRECT;
	}
	
	/**
	 * TODO: method should be DELETE
	 * @param asin the asin of the item to delete
	 * @return the view to render
	 */
	@RequestMapping(value="/items/{asin}", method = RequestMethod.POST)
	public String deleteItem(@PathVariable final String asin) {
		return Views.HOME_REDIRECT;
	}
}
