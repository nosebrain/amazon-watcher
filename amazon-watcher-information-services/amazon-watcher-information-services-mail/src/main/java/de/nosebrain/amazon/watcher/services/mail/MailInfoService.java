package de.nosebrain.amazon.watcher.services.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.util.Mailer;


/**
 * sends an mail to the provided address
 * 
 * @author nosebrain
 */
public class MailInfoService implements InformationService {
	
	private Mailer mailer;
	private InternetAddress address;

	@Override
	public void inform(final List<Item> items) {
		try {
			final MimeMessage message = this.mailer.createMessage();
			message.setSubject("Amazon Watcher | Overview");
			final StringBuilder content = new StringBuilder();
			content.append("<html><body><p>Some of your watched items have changed.</p><ul>");
			
			for (final Item item : items) {
				content.append("<li>");
				content.append("<a href=\"");
				content.append(item.getUrl().toString());
				content.append("\">");
				content.append(item.getName());
				content.append("</a>");
				content.append("<div class=\"info\">");
				final int size = item.getPriceHistories().size();
				
				final float currentPrice;
				if (size > 0) {
					currentPrice = item.getPriceHistories().get(size - 1).getValue();
				} else {
					// TODO: log illegal state
					currentPrice = -1.0f;
				}
				
				final float lastPrice;
				if (size > 1) {
					lastPrice = item.getPriceHistories().get(size - 2).getValue();
				} else {
					lastPrice = -1.0f;
				}
				
				switch (item.getMode()) {
				case PRICE_CHANGE:
					// TODO: i18n
					content.append("Price changed. ");
					content.append((lastPrice > currentPrice ? "Dropped" : "Increased"));
					content.append(" from ");
					content.append(lastPrice);
					content.append(" to ");
					content.append(currentPrice);
					content.append(".");
					break;
				case PRICE_LIMIT:
					final StringBuilder limit = new StringBuilder("The price dropped under your limit. It's now ");
					limit.append(currentPrice);
					limit.append(".");
					break;
				default:
					content.append("something happend");
					break;
				}
				content.append("</div></li>");
			}
			
			
			content.append("</ul></body></html>");
			message.setContent(content.toString(), "text/html; charset=utf-8");
			this.mailer.sendMessage(message, this.address);
		} catch (final MessagingException e) {
			// TODO: log
			System.err.println(e);
		}
	}

	/**
	 * @param mailer the mailer to set
	 */
	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(InternetAddress address) {
		this.address = address;
	}
}
