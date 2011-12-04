package de.nosebrain.amazon.watcher.webapp.controller;

import static de.nosebrain.util.ValidationUtils.present;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.webapp.services.UpdaterService;
import de.nosebrain.amazon.watcher.webapp.view.Views;

/**
 * 
 * @author nosebrain
 */
@Controller
public class HomepageController {

	private static final String SESSION_MESSAGE = "message";
	private static final String SESSION_MESSAGE_PARAM = "messageParam";


	public static void setMessage(final HttpSession session, final String message, final String messageParam) {
		session.setAttribute(SESSION_MESSAGE, message);
		session.setAttribute(SESSION_MESSAGE_PARAM, messageParam);
	}

	public static void copyMessage(final Model model, final HttpSession session) {
		final String message = (String) session.getAttribute(SESSION_MESSAGE);
		if (present(message)) {
			model.addAttribute(SESSION_MESSAGE, message);
			model.addAttribute(SESSION_MESSAGE_PARAM, session.getAttribute(SESSION_MESSAGE_PARAM));

			/*
			 * clear session
			 */
			setMessage(session, null, null);
		}
	}


	@Autowired
	protected AmazonWatcherService service;

	@Autowired
	private UpdaterService updaterService;

	/**
	 * @param model the model to fill
	 * @param session the session to get the message
	 * @return the homepage
	 */
	@RequestMapping(value = {"/", "/index"})
	public String home(final Model model, final HttpSession session) {
		final List<Observation> observations = this.service.getObservations();
		model.addAttribute("observations", observations);
		// TODO: inform about next update
		model.addAttribute("lastUpdateDate", this.updaterService.getLastUpdateDate());

		copyMessage(model, session);

		return Views.HOME;
	}
}
