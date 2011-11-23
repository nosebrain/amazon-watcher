package de.nosebrain.amazon.watcher.webapp.util.converter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.core.convert.ConversionFailedException;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 *
 */
public class StringToItemConverterTest {

	private static final StringToItemConverter CONVERTER = new StringToItemConverter();

	@Test
	public void testConvert() {
		final Item convert2 = CONVERTER.convert("http%3A%2F%2Famazon.com%2Fgp%2Fproduct%2FB0051VVOB2");

		assertNotNull(convert2);
		final Item convert3 = CONVERTER.convert("http://amamzon.de/gp/product/2FB0051VVOB2");

		assertNotNull(convert3);
		assertEquals(Amazon.DE, convert3.getSite());
		assertEquals("2FB0051VVOB2", convert3.getAsin());
	}


	@Test(expected = ConversionFailedException.class)
	public void testConvertFail() {
		CONVERTER.convert("test");
	}

}
