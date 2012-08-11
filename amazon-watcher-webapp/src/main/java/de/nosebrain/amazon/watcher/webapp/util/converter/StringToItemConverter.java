package de.nosebrain.amazon.watcher.webapp.util.converter;

import static de.nosebrain.util.ValidationUtils.present;

import org.springframework.core.convert.converter.Converter;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;

/**
 * 
 * @author nosebrain
 *
 */
public class StringToItemConverter implements Converter<String, Item> {

	@Override
	public Item convert(final String source) {
		if (!present(source)) {
			return null;
		}

		final String[] split = source.split("_");
		if (split.length != 2) {
			// TODO: error message and log
			return null;
		}

		final Item item = new Item();
		item.setAsin(split[1]);
		item.setSite(Amazon.valueOf(split[0]));
		return item;
	}
}
