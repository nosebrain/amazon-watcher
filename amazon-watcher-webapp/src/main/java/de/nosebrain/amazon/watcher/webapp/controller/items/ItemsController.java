package de.nosebrain.amazon.watcher.webapp.controller.items;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.webapp.util.Functions;

/**
 * 
 * @author nosebrain
 */
@Controller
public class ItemsController {

	@RequestMapping("/items/{item}/image")
	public String getItemUrl(@PathVariable final Item item) {
		return "redirect:" + Functions.getImageUrl(item);
	}
}
