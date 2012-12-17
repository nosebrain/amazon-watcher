package de.nosebrain.amazon.watcher.services.mail;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.MessageSource;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.util.ItemUtils;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.util.Mailer;


/**
 * sends an mail to the provided address
 * 
 * @author nosebrain
 */
public class MailInfoService implements InformationService {
	private static final String CONTENT_TYPE = "text/html; charset=utf-8";

	private static final String HEADER_SEPARATOR = " | ";
	
	
	private String systemName;
	private Mailer mailer;
	private InternetAddress address;
	private MessageSource messageSource;

	@Override
	public void testService(final Locale locale) throws Exception {
		final MimeMessage message = this.mailer.createMessage();
		message.setSubject(this.systemName + HEADER_SEPARATOR + this.messageSource.getMessage("testmessage.header", null, locale));
		final StringBuilder content = new StringBuilder();
		content.append("<html><body><p>");
		content.append(this.messageSource.getMessage("", null, locale));
		content.append("</ul></body></html>");
		message.setContent(content.toString(), CONTENT_TYPE);
		this.mailer.sendMessage(message, this.address);
	}

	@Override
	public void inform(final List<Observation> observations, final Locale locale) throws Exception {
		final MimeMessage message = this.mailer.createMessage();
		message.setSubject(this.systemName + HEADER_SEPARATOR + this.messageSource.getMessage("message.header", null, locale));
		final StringBuilder content = new StringBuilder();
		content.append("<html><body><p>");
		content.append(this.messageSource.getMessage("message.description", null, locale));
		content.append("</p><ul>");

		for (final Observation observation : observations) {
			final Item item = observation.getItem();
			final Amazon site = item.getSite();
			final NumberFormat numberFormat = NumberFormat.getInstance(site.getNumberFormatLocale());
			numberFormat.setMinimumFractionDigits(2);
			
			final String currency = site.getCurrency();
			content.append("<li>");
			content.append("<a href=\"");
			content.append(ItemUtils.generateUrlForItem(item));
			content.append("\">");
			content.append(observation.getName());
			content.append("</a>");
			content.append("<div class=\"info\">");
			
			final float currentPrice = ItemUtils.getCurrentPrice(item);
			final float lastPrice = ItemUtils.getLastPrice(item);
			
			final Object[] args = new Object[] {currency, numberFormat.format(currentPrice), numberFormat.format(lastPrice)};

			switch (observation.getMode()) {
			case PRICE_CHANGE:
				if (lastPrice > currentPrice) {
					content.append(this.messageSource.getMessage("message.description.change.increased", args, locale));
				} else {
					content.append(this.messageSource.getMessage("message.description.change.dropped", args, locale));
				}
				break;
			case PRICE_LIMIT:
				if (ItemUtils.overLimit(item, observation.getLimit())) {
					content.append(this.messageSource.getMessage("message.description.limit.over", args, locale));
				} else {
					content.append(this.messageSource.getMessage("message.description.limit.under", args, locale));
				}
				break;
			default:
				content.append("something happend");
				break;
			}
			content.append("</div></li>");
		}
		
		content.append("</ul></body></html>");
		message.setContent(content.toString(), CONTENT_TYPE);
		this.mailer.sendMessage(message, this.address);
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @param mailer the mailer to set
	 */
	public void setMailer(final Mailer mailer) {
		this.mailer = mailer;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(final InternetAddress address) {
		this.address = address;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
