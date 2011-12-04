package de.nosebrain.amazon.watcher.webapp.util.converter;

import org.springframework.core.convert.converter.Converter;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;

public class ItemToStringConverter implements Converter<Item, String> {

	@Override
	public String convert(final Item source) {
		return ItemUtils.generateUrlForItem(source);
	}
}
