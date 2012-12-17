package de.nosebrain.amazon.watcher.webapp.controller.user.settings;

import static de.nosebrain.util.ValidationUtils.present;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.model.User;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.webapp.services.InformationServiceBuilder;
import de.nosebrain.amazon.watcher.webapp.validation.InfoServiceValidator;
import de.nosebrain.amazon.watcher.webapp.view.Views;
import de.nosebrain.common.exception.ResourceNotFoundException;

/**
 * 
 * @author nosebrain
 */
@Controller
public class InfoServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(InfoServiceController.class);

	private static final String INFOSERVICE_TAB = "infoServices";
	
	
	@Autowired
	private InformationServiceBuilder builder;

	/**
	 * inits the binder
	 * @param binder
	 */
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.setValidator(new InfoServiceValidator());
	}

	/**
	 * adds a new {@link InfoService} for the logged in user
	 * @param infoService
	 * @param result
	 * @param redirectAttributes
	 * @return the settings view
	 */
	@RequestMapping(value = Views.INFO_SERVICES_PATH, method = RequestMethod.POST)
	public String addInfoService(final AmazonWatcherService service, @Valid final InfoService infoService, final Errors result, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return Views.SETTINGS;
		}

		infoService.recalculateHash();
		final InfoService infoServiceInDb = service.getInfoService(infoService.getHash());
		if (present(infoServiceInDb)) {
			result.reject("infoservice.duplicate");
			return Views.SETTINGS;
		}

		service.addInformationService(infoService);
		redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE, "settings.infoServices." + infoService.getInfoServiceKey() + ".added");
		redirectAttributes.addFlashAttribute(UserSettingsController.ACTIVE_TAB, INFOSERVICE_TAB); // TODO: show service info tab
		return Views.SETTINGS_REDIRECT;
	}

	/**
	 * method for deleting an {@link InfoService}
	 * @param hash
	 * @param model
	 * @param redirectAttributes
	 * @return the info service
	 */
	@RequestMapping(value = Views.INFO_SERVICES_PATH + "/{hash}", method = RequestMethod.DELETE)
	public String deleteInfoService(final AmazonWatcherService service, @PathVariable final String hash, final Model model, final RedirectAttributes redirectAttributes) {
		final InfoService infoServiceInDb = service.getInfoService(hash);

		if (!present(infoServiceInDb)) {
			// TODO: reject error
			return Views.SETTINGS_PATH;
		}

		service.removeInformationService(hash);
		// TODO: message
		redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE, "");
		redirectAttributes.addFlashAttribute(UserSettingsController.ACTIVE_TAB, INFOSERVICE_TAB);

		return Views.SETTINGS_REDIRECT;
	}

	/**
	 * method for testing the info service
	 * 
	 * @param hash
	 * @param redirectAttributes
	 * @return the settings page
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = Views.INFO_SERVICES_PATH + "/{hash}/test")
	public String testInfoService(final AmazonWatcherService service, @PathVariable final String hash, final RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		final InfoService infoService = service.getInfoService(hash);

		if (!present(infoService)) {
			// errors reject
			throw new ResourceNotFoundException();
		}

		final User loggedinUser = service.getLoggedInUser();
		try {
			final InformationService informationService = this.builder.createInformationServiceFromInfoService(infoService);
			informationService.testService(loggedinUser.getSettings().getLanguage());
		} catch (final Exception e) {
			LOGGER.error("error while testing info service '" + hash + "'for user '" + loggedinUser.getName() + "'", hash, e);
		}

		redirectAttributes.addFlashAttribute(UserSettingsController.ACTIVE_TAB, INFOSERVICE_TAB);
		return Views.SETTINGS_REDIRECT;
	}
}
