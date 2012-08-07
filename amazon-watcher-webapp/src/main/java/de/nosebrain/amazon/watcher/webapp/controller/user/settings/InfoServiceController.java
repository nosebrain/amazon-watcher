package de.nosebrain.amazon.watcher.webapp.controller.user.settings;

import static de.nosebrain.util.ValidationUtils.present;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.InfoService;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.webapp.services.InformationServiceBuilder;
import de.nosebrain.amazon.watcher.webapp.util.ControllerUtils;
import de.nosebrain.amazon.watcher.webapp.validation.InfoServiceValidator;
import de.nosebrain.amazon.watcher.webapp.view.Views;
import de.nosebrain.common.exception.ResourceNotFoundException;

/**
 * 
 * @author nosebrain
 */
@Controller
@Scope("request")
public class InfoServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InfoServiceController.class);


	private static final String INFO_SERVICES_PATH = UserSettingsController.SETTINGS_PATH + "/infoServices";


	@Autowired
	private AmazonWatcherService service;

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
	 * @param session the session
	 * @return the settings view
	 */
	@RequestMapping(value = INFO_SERVICES_PATH, method = RequestMethod.POST)
	public String addInfoService(@Valid final InfoService infoService, final BindingResult result, final HttpSession session) {
		if (result.hasErrors()) {
			return UserSettingsController.SETTINGS;
		}

		infoService.recalculateHash();
		final InfoService infoServiceInDb = this.service.getInfoService(infoService.getHash());
		if (present(infoServiceInDb)) {
			result.reject("infoservice.duplicate");
			return UserSettingsController.SETTINGS;
		}

		this.service.addInformationService(infoService);
		ControllerUtils.setMessage(session, "settings.infoServices." + infoService.getInfoServiceKey() + ".added");
		return Views.REDIRECT + UserSettingsController.SETTINGS_PATH; // TODO: show service info tab
	}

	/**
	 * method for testing the info service
	 * 
	 * @param hash
	 * @param principal
	 * @return the settings page
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = INFO_SERVICES_PATH + "/{hash}/test")
	public String testInfoService(@PathVariable final String hash, final Principal principal) throws ResourceNotFoundException {
		final InfoService infoService = this.service.getInfoService(hash);

		if (!present(infoService)) {
			throw new ResourceNotFoundException();
		}

		try {
			final InformationService informationService = this.builder.createInformationServiceFromInfoService(infoService);
			informationService.testService();
		} catch (final Exception e) {
			LOGGER.error("error while testing info service '" + hash + "'for user '" + principal.getName() + "'", hash, e);
		}

		return Views.REDIRECT + UserSettingsController.SETTINGS_PATH;
	}
}
