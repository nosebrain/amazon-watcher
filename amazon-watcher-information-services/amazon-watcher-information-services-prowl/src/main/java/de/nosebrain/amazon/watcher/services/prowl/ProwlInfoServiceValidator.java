package de.nosebrain.amazon.watcher.services.prowl;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProwlInfoServiceValidator implements Validator {
	private static final Pattern VALID_PATTERN = Pattern.compile("[a-f0-9]{40}");
	
	@Override
	public boolean supports(final Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final String settings = (String) target;
		
		if (settings.contains(",")) {
			errors.rejectValue("", "infoService.prowl.settings.invalid.multiple");
		} else if (!VALID_PATTERN.matcher(settings).matches()) {
			errors.rejectValue("", "infoService.prowl.settings.invalid");
		}
		
	}
}
