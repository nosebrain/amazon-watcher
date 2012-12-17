package de.nosebrain.amazon.watcher.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
public class ErrorController {

	/**
	 * @param model the model
	 * @return page not found
	 */
	@RequestMapping("/errors/{status}")
	public String error(@PathVariable("status") final int statusCode, final Model model) {
		model.addAttribute("status", statusCode);
		return Views.ERROR_VIEW;
	}
}
