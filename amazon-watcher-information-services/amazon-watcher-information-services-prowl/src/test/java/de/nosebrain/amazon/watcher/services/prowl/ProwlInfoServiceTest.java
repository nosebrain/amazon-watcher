package de.nosebrain.amazon.watcher.services.prowl;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.nosebrain.amazon.watcher.model.Amazon;
import de.nosebrain.amazon.watcher.model.Item;
import de.nosebrain.amazon.watcher.model.Observation;
import de.nosebrain.amazon.watcher.model.ObservationMode;
import de.nosebrain.amazon.watcher.model.PriceHistory;
import de.nosebrain.prowl.Notification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/TestContextProwl.xml")
public class ProwlInfoServiceTest {
	
	private ProwlInfoService service = new ProwlInfoService();
	
	@Autowired
	private ProwlInformationServiceFactory factory;
	
	@Before
	public void createService() {
		this.service = this.factory.createService();
	}

	@Test
	public void testCreateNotification() throws Exception {
		final Observation observation = new Observation();
		observation.setMode(ObservationMode.PRICE_CHANGE);
		
		final Item item = new Item();
		item.setAsin("test");
		item.setSite(Amazon.DE);
		
		final PriceHistory last = new PriceHistory();
		last.setValue(10f);
		final PriceHistory before = new PriceHistory();
		before.setValue(8.1f);
		item.setPriceHistories(Arrays.asList(last, before));
		
		observation.setItem(item);
		observation.setName("Hallo");
		
		final Notification notification = this.service.createNotification(observation, Locale.US);
		assertEquals("Price changed. Dropped from 10,00€ to 8,10€.", notification.getDescription());
	}

}
