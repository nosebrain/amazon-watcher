package de.nosebrain.amazon.watcher.webapp.validation;

import static de.nosebrain.util.ValidationUtils.present;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.ObservationMode;

/**
 * 
 * @author nosebrain
 *
 */
public class ObservationValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return Observation.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final Observation observation = (Observation) target;

		if (!present(observation.getItem())) {
			errors.rejectValue("item", "observation.item.invalid");
			// TODO: test item
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "observation.name.invalid");
		ValidationUtils.rejectIfEmpty(errors, "mode", "observation.mode.invalid");

		if (ObservationMode.PRICE_LIMIT.equals(observation.getMode())) {
			final Float limit = observation.getLimit();

			if (limit == null || limit <= 0.0) {
				errors.rejectValue("limit", "observation.limit.invalid");
			}
		}
	}

}
