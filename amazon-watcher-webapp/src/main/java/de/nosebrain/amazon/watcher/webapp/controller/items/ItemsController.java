package de.nosebrain.amazon.watcher.webapp.controller.items;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
}
