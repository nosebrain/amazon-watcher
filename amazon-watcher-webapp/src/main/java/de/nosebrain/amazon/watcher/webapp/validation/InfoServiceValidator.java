package de.nosebrain.amazon.watcher.webapp.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.nosebrain.amazon.watcher.model.InfoService;

/**
 * 
 * @author nosebrain
 */
public class InfoServiceValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return InfoService.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "infoServiceKey", "");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "settings", "");

		// TODO: check settings for valid values
	}
}
