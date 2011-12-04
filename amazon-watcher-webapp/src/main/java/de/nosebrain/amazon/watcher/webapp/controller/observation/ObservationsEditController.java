package de.nosebrain.amazon.watcher.webapp.controller.observation;

import static de.nosebrain.util.ValidationUtils.present;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.nosebrain.amazon.watcher.AmazonWatcherService;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;
import de.nosebrain.amazon.watcher.webapp.controller.HomepageController;
import de.nosebrain.amazon.watcher.webapp.validation.ObservationValidator;
import de.nosebrain.amazon.watcher.webapp.view.Views;
import de.nosebrain.common.exception.ResourceNotFoundException;

/**
 * 
 * @author nosebrain
 */
@Controller
public class ObservationsEditController {

	@Autowired
	private AmazonWatcherService service;

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
	 * @param observation the observation to create
	 * @param result the validation result
	 * @return the view to render
	 */
	@RequestMapping(value="/" + Views.OBSERVATIONS, method = RequestMethod.POST)
	public String saveItem(@Valid final Observation observation, final BindingResult result) {
		if (result.hasErrors()) {
			return Views.OBSERVATION_EDIT;
		}

		final Item item = observation.getItem();
		final Observation observationInDB = this.service.getObservationByItem(item);

		if (observationInDB != null) {
			// TODO: display error message
			return "redirect:observations/" + ItemUtils.generateUrlForItem(item) + "/edit";
		}

		this.service.addObservation(observation);
		return Views.HOME_REDIRECT;
	}

	/**
	 * edit an existing item (view)
	 * @param item the item
	 * @param model the model to use
	 * @return the item form
	 * @throws ResourceNotFoundException if the observation could not be found
	 */
	@RequestMapping(value="/observations/{item}/edit", method = RequestMethod.GET)
	public String editObservationForm(@PathVariable final Item item, final Model model) throws ResourceNotFoundException {
		final Observation observation = this.getObservationByItem(item);
		return this.observationEditForm(observation, model);
	}

	private Observation getObservationByItem(final Item item) throws ResourceNotFoundException {
		final Observation observation = this.service.getObservationByItem(item);
		if (observation == null) {
			throw new ResourceNotFoundException();
		}
		return observation;
	}

	/**
	 * TODO: add / to end
	 * edit an existing item (view)
	 * @param item the item
	 * @param model the model to use
	 * @return the item form
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/observations/{item:.*}", method = RequestMethod.GET)
	public String getItem(@PathVariable final Item item, final Model model) throws ResourceNotFoundException {
		final Item itemDetails = this.service.getItemDetails(item);

		if (!present(itemDetails)) {
			throw new ResourceNotFoundException();
		}

		model.addAttribute(itemDetails);
		return Views.HOME_REDIRECT;
	}

	/**
	 * TODO: add / to end
	 * edit an existing item (view)
	 * @param item the item to update
	 * @param observation
	 * @param session the session to store the message
	 * @return the item form
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/observations/{item:.*}", method = RequestMethod.PUT)
	public String updateItem(@PathVariable final Item item, @Valid final Observation observation, final HttpSession session) throws ResourceNotFoundException {
		/*
		 * check if user has observation for item
		 */
		this.getObservationByItem(item);
		this.service.updateObservation(item, observation);

		HomepageController.setMessage(session, "observation.edit.success", observation.getName());
		return Views.HOME_REDIRECT;
	}

	/**
	 * TODO: add / to end
	 * @param item the item of the observation to delete
	 * @return the view to render
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/observations/{item:.*}", method = RequestMethod.DELETE)
	public String deleteItem(@PathVariable final Item item) throws ResourceNotFoundException {
		this.getObservationByItem(item);
		this.service.removeObservation(item);

		// TODO: success message
		return Views.HOME_REDIRECT;
	}
}
