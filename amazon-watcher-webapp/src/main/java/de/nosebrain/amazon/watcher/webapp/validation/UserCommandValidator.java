package de.nosebrain.amazon.watcher.webapp.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.nosebrain.amazon.watcher.webapp.command.user.UserCommand;

public class UserCommandValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return UserCommand.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final UserCommand command = (UserCommand) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.authority.password.invalid");

	}
}
