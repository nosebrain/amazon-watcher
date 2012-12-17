package de.nosebrain.amazon.watcher.webapp.controller.observation;

import static de.nosebrain.util.ValidationUtils.present;

import javax.validation.Valid;

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
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.webapp.validation.ObservationValidator;
import de.nosebrain.amazon.watcher.webapp.view.Views;
import de.nosebrain.common.exception.ResourceNotFoundException;

/**
 * 
 * @author nosebrain
 */
@Controller
public class ObservationsEditController {

	/**
	 * adds {@link ObservationValidator} to the binder
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.setValidator(new ObservationValidator());
	}

	/**
	 * @param observation the item to edit
	 * @param model the model to fill
	 * @return the item form view
	 */
	@RequestMapping(value= "/" + Views.OBSERVATION_EDIT, method = RequestMethod.GET)
	public String observationEditForm(final Observation observation, final Model model) {
		model.addAttribute(observation);
		return Views.OBSERVATION_EDIT;
	}

	/**
	 * edit an existing item (view)
	 * @param item the item
	 * @param model the model to use
	 * @return the item form
	 * @throws ResourceNotFoundException if the observation could not be found
	 */
	@RequestMapping(value="/" + Views.OBSERVATIONS + "/{item}/edit", method = RequestMethod.GET)
	public String editObservationForm(final AmazonWatcherService service, @PathVariable final Item item, final Model model) throws ResourceNotFoundException {
		final Observation observation = this.getObservationByItem(service, item);
		return this.observationEditForm(observation, model);
	}

	/**
	 * @param observation the observation to create
	 * @param result the validation result
	 * @param redirectAttributes
	 * @return the view to render
	 */
	@RequestMapping(value="/" + Views.OBSERVATIONS, method = RequestMethod.POST)
	public String createObservation(final AmazonWatcherService service, @Valid final Observation observation, final Errors result, final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return Views.OBSERVATION_EDIT;
		}

		final Item item = observation.getItem();
		final Observation observationInDB = service.getObservationByItem(item);

		if (present(observationInDB)) {
			result.reject("observation.duplicate");
			return Views.getObservationEditUrl(item);
		}

		service.addObservation(observation);

		redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE, "observation.create.success");
		redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE_PARAMS, observation.getName());
		return Views.HOME_REDIRECT;
	}

	private Observation getObservationByItem(final AmazonWatcherService service, final Item item) throws ResourceNotFoundException {
		final Observation observation = service.getObservationByItem(item);
		if (observation == null) {
			throw new ResourceNotFoundException();
		}
		return observation;
	}

	/**
	 * edit an existing observation
	 * 
	 * @param item the item to update
	 * @param observation
	 * @param errors the validation errors
	 * @param redirectAttributes
	 * @return the item form
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/" + Views.OBSERVATIONS + "/{item}", method = RequestMethod.PUT)
	public String updateObservation(final AmazonWatcherService service, @PathVariable final Item item, @Valid final Observation observation, final Errors errors, final RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		// TODO: check item

		if (errors.hasErrors()) {
			return Views.OBSERVATION_EDIT;
		}
		/*
		 * check if user has observation for item
		 */
		this.getObservationByItem(service, item);
		service.updateObservation(item, observation);

		redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE, "observation.edit.success");
		redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE_PARAMS, observation.getName());

		return Views.HOME_REDIRECT;
	}

	/**
	 * delete an existing observation
	 * 
	 * @param item the item of the observation to delete
	 * @param redirectAttributes
	 * @return the view to render
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/" + Views.OBSERVATIONS + "/{item}", method = RequestMethod.DELETE)
	public String deleteObservation(final AmazonWatcherService service, @PathVariable final Item item, final RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		final Observation observation = this.getObservationByItem(service, item);

		if (present(observation)) {
			service.removeObservation(item);
			redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE, "observation.delete.success");
			redirectAttributes.addFlashAttribute(Views.SESSION_MESSAGE_PARAMS, observation.getName());
			return Views.HOME_REDIRECT;
		}

		// TODO:
		// result.reject("observation.not_found");
		return Views.HOME_REDIRECT;
	}
}
