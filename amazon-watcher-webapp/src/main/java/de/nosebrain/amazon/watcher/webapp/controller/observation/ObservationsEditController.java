package de.nosebrain.amazon.watcher.webapp.controller.observation;

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
			return "redirect:items/" + ItemUtils.generateUrlForItem(item) + "/edit";
		}

		this.service.addObservation(observation);
		return Views.HOME_REDIRECT;
	}

	/**
	 * edit an existing item (view)
	 * @param asin the asin of the item
	 * @param model the model to use
	 * @return the item form
	 */
	@RequestMapping(value="/observations/{item}/edit", method = RequestMethod.GET)
	public String editObservationForm(@PathVariable final Item item, final Model model) throws ResourceNotFoundException {
		final Observation observation = this.service.getObservationByItem(item);

		if (observation == null) {
			throw new ResourceNotFoundException();
		}

		return this.observationEditForm(observation, model);
	}

	/**
	 * edit an existing item (view)
	 * @param asin the asin of the item
	 * @param item the item to update
	 * @param model the model to use
	 * @return the item form
	 */
	@RequestMapping(value="/observations/{item}", method = { RequestMethod.POST, RequestMethod.PUT })
	public String updateItem(@PathVariable final Item item, @Valid final Observation observation, final Model model) {
		// FIXME: item and validation
		// TODO: implement method
		// TODO: validation
		return "TODO";
	}

	/**
	 * @param asin the asin of the item to delete
	 * @return the view to render
	 */
	@RequestMapping(value="/observations/{item}", method = RequestMethod.DELETE)
	public String deleteItem(@PathVariable final Item item) {
		// TODO: implement me
		return Views.HOME_REDIRECT;
	}
}
