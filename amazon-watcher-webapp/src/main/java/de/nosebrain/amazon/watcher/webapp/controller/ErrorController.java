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

	private static String showError(final Model model, final int errorCode) {
		model.addAttribute("status", errorCode);
		return "errors/errors";
	}

	/**
	 * @param model the model
	 * @return page not found
	 */
	@RequestMapping("/errors/404")
	public String error_404(final Model model) {
		return showError(model, 404);
	}

	/**
	 * @param model the model
	 * @return method not allowed
	 */
	@RequestMapping("/errors/405")
	public String error_405(final Model model) {
		return showError(model, 405);
	}

	/**
	 * @param model the model
	 * @return ?
	 */
	@RequestMapping("/errors/401")
	public String error_401(final Model model) {
		return showError(model, 401);
	}

	/**
	 * @param model the model
	 * @return internal server error
	 */
	@RequestMapping("/errors/500")
	public String error500(final Model model) {
		return showError(model, 500);
	}
}
