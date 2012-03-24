package de.nosebrain.amazon.watcher.webapp.controller.items;

import static de.nosebrain.util.ValidationUtils.present;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.webapp.util.Functions;

/**
 * 
 * @author nosebrain
 */
@Controller
public class ItemsController {

	@Autowired
	private AmazonWatcherService service;

	@RequestMapping("/items/{item}/image")
	public String getItemUrl(@PathVariable final Item item) {
		return "redirect:" + Functions.getImageUrl(item);
	}

	@RequestMapping("/items/{item}/recommandation")
	public String getRecommendation(@PathVariable final Item item, final Model model) {
		Item itemDetails = this.service.getItemDetails(item);

		/*
		 * if we find no details in our database return the provided item
		 * (this contains at least the asin and the site info)
		 * TODO: update price with the updater service?
		 */
		if (present(itemDetails)) {
			itemDetails = item;
		}
		model.addAttribute("item", itemDetails);
		return "special/empty";
	}
}
