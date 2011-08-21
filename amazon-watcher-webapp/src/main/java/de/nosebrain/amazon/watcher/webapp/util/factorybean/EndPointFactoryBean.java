package de.nosebrain.amazon.watcher.webapp.util.factorybean;

import org.springframework.beans.factory.FactoryBean;

import de.nosebrain.amazon.watcher.model.Amazon;

/**
 * 
 * @author nosebrain
 */
public class EndPointFactoryBean implements FactoryBean<String> {

	private Amazon amazon;
	
	@Override
	public String getObject() throws Exception {
		return this.amazon.getUrl();
	}

	@Override
	public Class<?> getObjectType() {
		return String.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	/**
	 * @param amazon the amazon to set
	 */
	public void setAmazon(Amazon amazon) {
		this.amazon = amazon;
	}	
}
