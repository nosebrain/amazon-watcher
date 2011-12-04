package de.nosebrain.amazon.watcher.webapp.util.converter;

import static de.nosebrain.util.ValidationUtils.present;

import java.net.URL;
import java.net.URLDecoder;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;

/**
 * 
 * @author nosebrain
 *
 */
public class StringToItemConverter implements Converter<String, Item> {

	private static Item convertToItem(final URL url) {
		final Item item = new Item();
		final String asin = ItemUtils.extractASIN(url);
		if (!present(asin)) {
			throw new IllegalArgumentException("can't get asin for url");
		}
		item.setAsin(asin);
		final Amazon site = ItemUtils.extractAmazon(url);
		if (!present(site)) {
			throw new IllegalArgumentException("can't get site for url");
		}
		item.setSite(site);
		return item;
	}

	@Override
	public Item convert(final String source) {
		if (!present(source)) {
			return null;
		}

		try {
			final URL url = new URL(source);
			return convertToItem(url);
		} catch (Exception e) {
			/*
			 * FIXME: annoted handler doesn't url decode parameters!
			 */
			try {
				final URL url = new URL(URLDecoder.decode(source, "UTF-8"));
				return convertToItem(url);
			} catch (final Exception ex) {
				e = ex;
			}

			// TODO: log
			throw new ConversionFailedException(TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Item.class), source, e);
		}
	}



}
