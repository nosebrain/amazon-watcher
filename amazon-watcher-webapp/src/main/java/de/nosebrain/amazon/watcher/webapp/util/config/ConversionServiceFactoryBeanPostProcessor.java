package de.nosebrain.amazon.watcher.webapp.util.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;

/**
 * 
 * @author nosebrain
 */
public class ConversionServiceFactoryBeanPostProcessor implements BeanFactoryPostProcessor, Ordered {

	private int order = Ordered.HIGHEST_PRECEDENCE;
	private ConversionService conversionService;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.setConversionService(this.conversionService);
	}
	
	/**
	 * @param conversionService the conversion service to set
	 */
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}
	
	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

}
