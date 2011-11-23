package de.nosebrain.amazon.watcher.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequestMapping("/errors/404")
	public String error_404(final Model model) {
		return this.showError(model, 404);
	}

	/**
	 * @return method not allowed
	 */
	@RequestMapping("/errors/405")
	public String error_405(final Model model) {
		return this.showError(model, 405);
	}

	/**
	 * @return ?
	 */
	@RequestMapping("/errors/401")
	public String error_401(final Model model) {
		return this.showError(model, 401);
	}

	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("/errors/500")
	public String error500(final Model model) {
		return this.showError(model, 500);
	}

	private String showError(final Model model, final int errorCode) {
		model.addAttribute("status", errorCode);
		return "errors/errors";
	}
}
