package de.nosebrain.amazon.watcher.webapp.util.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ReloadableMultiResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {
	private static final String PROPERTIES_SUFFIX = ".properties";
	private static final String XML_SUFFIX = ".xml";


	private final Map<String, PropertiesHolder> cachedProperties = new HashMap<String, PropertiesHolder>();
	private int cacheMillis;
	private ResourcePatternResolver resourceLoader;

	/**
	 * Refresh the PropertiesHolder for the given bundle filename.
	 * The holder can be <code>null</code> if not cached before, or a timed-out cache entry
	 * (potentially getting re-validated against the current last-modified timestamp).
	 * @param filename the bundle filename (basename + Locale)
	 * @param propHolder the current PropertiesHolder for the bundle
	 */
	@Override
	protected PropertiesHolder refreshProperties(final String filename, PropertiesHolder propHolder) {
		final long refreshTimestamp = this.cacheMillis < 0 ? -1 : System.currentTimeMillis();
		try {
			Resource[] resources = this.resourceLoader.getResources(filename + PROPERTIES_SUFFIX);
			if (!this.exists(resources)) {
				resources = this.resourceLoader.getResources(filename + XML_SUFFIX);
			}

			if (this.exists(resources)) {
				long fileTimestamp = -1;
				if (this.cacheMillis >= 0) {
					// Last-modified timestamp of file will just be read if caching with timeout.
					try {
						fileTimestamp = this.getLastModified(resources);
						if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
							if (this.logger.isDebugEnabled()) {
								this.logger.debug("Re-caching properties for filename [" + filename + "] - file hasn't been modified");
							}
							propHolder.setRefreshTimestamp(refreshTimestamp);
							return propHolder;
						}
					}
					catch (final IOException ex) {
						// Probably a class path resource: cache it forever.
						if (this.logger.isDebugEnabled()) {
							this.logger.debug(
									resources + " could not be resolved in the file system - assuming that is hasn't changed", ex);
						}
						fileTimestamp = -1;
					}
				}
				try {
					final Properties props = new Properties();
					propHolder = new PropertiesHolder(props, fileTimestamp);

					for (final Resource resource : resources) {
						props.putAll(this.loadProperties(resource, filename));
					}
				} catch (final IOException ex) {
					if (this.logger.isWarnEnabled()) {
						this.logger.warn("Could not parse properties file [" + resources + "]", ex);
					}
					// Empty holder representing "not valid".
					propHolder = new PropertiesHolder();
				}
			} else {
				// Resource does not exist.
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("No properties file found for [" + filename + "] - neither plain properties nor XML");
				}
				// Empty holder representing "not found".
				propHolder = new PropertiesHolder();
			}
		} catch (final IOException e) {
			propHolder = new PropertiesHolder();
		}

		propHolder.setRefreshTimestamp(refreshTimestamp);
		this.cachedProperties.put(filename, propHolder);
		return propHolder;
	}

	private long getLastModified(final Resource[] resources) throws IOException {
		long latestModified = Long.MIN_VALUE;
		for (final Resource resource : resources) {
			latestModified = Math.min(latestModified, resource.lastModified());
		}

		return latestModified;
	}

	private boolean exists(final Resource[] resources) {
		for (final Resource resource : resources) {
			if (resource.exists()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setResourceLoader(final ResourceLoader resourceLoader) {
		super.setResourceLoader(resourceLoader);

		if (resourceLoader instanceof ResourcePatternResolver) {
			this.resourceLoader = (ResourcePatternResolver) resourceLoader;
		}
	}
}
