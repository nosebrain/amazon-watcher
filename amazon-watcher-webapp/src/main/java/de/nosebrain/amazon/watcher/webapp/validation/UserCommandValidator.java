package de.nosebrain.amazon.watcher.webapp.validation;

import static de.nosebrain.util.ValidationUtils.present;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.nosebrain.amazon.watcher.webapp.command.user.UserCommand;

/**
 * 
 * @author nosebrain
 */
public class UserCommandValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return UserCommand.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final UserCommand command = (UserCommand) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.authority.password.invalid");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retype", "user.authority.password.invalid");
		final String password = command.getPassword();
		if (present(password) && !password.equals(command.getRetype())) {
			errors.rejectValue("retype", "user.password.retype");
		}

		errors.pushNestedPath("user");
		ValidationUtils.invokeValidator(new UserValidator(), command.getUser(), errors);
		errors.popNestedPath();
	}
}
