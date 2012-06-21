package de.nosebrain.amazon.watcher.webapp.util;

import static de.nosebrain.util.ValidationUtils.present;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

/**
 * 
 * @author nosebrain
 *
 */
public class ControllerUtils {
	private static final String SESSION_MESSAGE_PARAMS = "messageParams";
	private static final String SESSION_MESSAGE = "message";

	/**
	 * 
	 * @param model
	 * @param session
	 */
	public static void copyMessage(final Model model, final HttpSession session) {
		final String message = (String) session.getAttribute(ControllerUtils.SESSION_MESSAGE);
		if (present(message)) {
			model.addAttribute(ControllerUtils.SESSION_MESSAGE, message);
			model.addAttribute(ControllerUtils.SESSION_MESSAGE_PARAMS, session.getAttribute(ControllerUtils.SESSION_MESSAGE_PARAMS));

			/*
			 * clear session
			 */
			ControllerUtils.setMessage(session, null, (String[]) null);
		}
	}


	/**
	 * @param session
	 * @param message
	 * @param messageParam
	 */
	public static void setMessage(final HttpSession session, final String message, final String... messageParam) {
		session.setAttribute(SESSION_MESSAGE, message);
		session.setAttribute(SESSION_MESSAGE_PARAMS, messageParam);
	}

}
