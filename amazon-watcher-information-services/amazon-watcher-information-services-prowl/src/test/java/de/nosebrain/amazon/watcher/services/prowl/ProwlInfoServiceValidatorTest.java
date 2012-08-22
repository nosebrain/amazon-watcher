package de.nosebrain.amazon.watcher.services.prowl;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.springframework.validation.BindException;


public class ProwlInfoServiceValidatorTest {
	
	private static final ProwlInfoServiceValidator VALIDATOR = new ProwlInfoServiceValidator();

	@Test
	public void testValidate() throws Exception {
		String settings = "as";
				
		final BindException errors = this.getErrors(settings);
		assertTrue(errors.hasErrors());
		
		settings = "6c8ba7a845feb50eea3c21dd2660282026602820";
		final BindException errors2 = this.getErrors(settings);
		assertFalse(errors2.hasErrors());
		
		settings = settings + "," + settings;
		final BindException errors3 = this.getErrors(settings);
		assertTrue(errors3.hasErrors());
	}

	private BindException getErrors(final String settings) {
		final BindException errors = new BindException(settings, "");
		VALIDATOR.validate(settings, errors);
		return errors;
	}

}
