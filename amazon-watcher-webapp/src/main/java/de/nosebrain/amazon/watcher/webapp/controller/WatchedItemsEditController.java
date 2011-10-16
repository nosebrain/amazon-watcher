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
	 * @param item the item to edit
	 * @param model the model to fill
	 * @return the item form view
	 */
	@RequestMapping(value="/items/edit", method = RequestMethod.GET)
	public String itemEditForm(final Item item, final Model model) {
		model.addAttribute(item);
		return "items/edit";
	}
	
	/**
	 * @param item the item to create
	 * @return the view to render
	 */
	@RequestMapping(value="/items", method = RequestMethod.POST)
	public String saveItem(final Item item) {
		// TODO: validation
		// e.g. mode == LIMIT limit set not negative not 0
		// don't forget to reset the asin
		this.service.watchItem(item);
		
		return Views.HOME_REDIRECT;
	}
	
	/**
	 * edit an existing item (view)
	 * @param asin the asin of the item
	 * @param model the model to use
	 * @return the item form
	 */
	@RequestMapping(value="/items/{asin}/edit", method = RequestMethod.GET)
	public String editItemForm(@PathVariable final String asin, final Model model) {
		final Item item = this.service.getItemByAsin(asin);
		
		return this.itemEditForm(item, model);
	}
	
	/**
	 * edit an existing item (view)
	 * @param asin the asin of the item
	 * @param item the item to update
	 * @param model the model to use
	 * @return the item form
	 */
	@RequestMapping(value="/items/{asin}/edit", method = { RequestMethod.POST, RequestMethod.PUT })
	public String updateItem(@PathVariable final String asin, final Item item, final Model model) {
		// TODO: implement method
		// TODO: validation
		return "TODO";
	}
	
	/**
	 * @param asin the asin of the item to delete
	 * @return the view to render
	 */
	@RequestMapping(value="/items/{asin}", method = RequestMethod.DELETE)
	public String deleteItem(@PathVariable final String asin) {
		return Views.HOME_REDIRECT;
	}
}
