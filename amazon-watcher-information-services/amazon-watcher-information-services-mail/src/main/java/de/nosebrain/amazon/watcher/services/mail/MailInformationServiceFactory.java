package de.nosebrain.amazon.watcher.services.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import de.nosebrain.amazon.watcher.services.InformationService;
import de.nosebrain.amazon.watcher.services.InformationServiceFactory;

/**
 * 
 * @author nosebrain
 */
public abstract class MailInformationServiceFactory implements InformationServiceFactory {

	@Override
	public InformationService createInformationService(final String settings) {
		try {
			final MailInfoService service = this.createService();
			service.setAddress(new InternetAddress(settings));
			return service;
		} catch (final AddressException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return a configurated {@link MailInfoService}
	 */
	protected abstract MailInfoService createService();
}
