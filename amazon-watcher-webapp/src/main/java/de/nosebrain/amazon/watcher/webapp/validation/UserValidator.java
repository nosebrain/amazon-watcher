package de.nosebrain.amazon.watcher.webapp.validation;

import static de.nosebrain.util.ValidationUtils.present;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.nosebrain.amazon.watcher.model.User;

/**
 * 
 * @author nosebrain
 */
public class UserValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final User user = (User) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "user.name.invalid");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "user.mail.invalid");

		final String mail = user.getMail();
		if (present(mail)) {

		}
	}

}
