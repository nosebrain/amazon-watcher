package de.nosebrain.amazon.watcher.webapp.controller.items;

import static de.nosebrain.util.ValidationUtils.present;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;
import de.nosebrain.amazon.watcher.webapp.util.Functions;

/**
 * 
 * @author nosebrain
 */
@Controller
@Scope("request")
public class ItemsController {

	@Autowired
	private AmazonWatcherService service;

	@RequestMapping("/items/image")
	public String getItemUrl(@RequestParam("url") final URL url) {
		final Item item = ItemUtils.extractItem(url);
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
