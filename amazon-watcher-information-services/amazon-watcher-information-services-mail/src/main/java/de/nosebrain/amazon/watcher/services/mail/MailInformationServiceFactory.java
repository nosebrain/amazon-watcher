package de.nosebrain.amazon.watcher.services.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;
import de.nosebrain.util.Mailer;

/**
 * 
 * @author nosebrain
 *
 */
public class MailInformationServiceFactory implements InformationServiceFactory {

	private Mailer mailer;

	@Override
	public InformationService createInformationService(final String settings) {
		final MailInfoService service = new MailInfoService();
		service.setMailer(this.mailer);
		try {
			service.setAddress(new InternetAddress(settings));
		} catch (final AddressException e) {
			// TODO: log
			return null;
		}
		return service;
	}

	/**
	 * @param mailer the mailer to set
	 */
	public void setMailer(final Mailer mailer) {
		this.mailer = mailer;
	}

}
