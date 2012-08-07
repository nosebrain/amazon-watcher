package de.nosebrain.amazon.watcher.database.util;

import de.nosebrain.amazon.watcher.model.InfoService;

public class InfoServiceParam extends UserAwareParam {

	private InfoService infoService;
	private String hash;

	public InfoServiceParam() {
		// noop
	}

	public InfoServiceParam(final InfoService infoService) {
		this.infoService = infoService;
	}

	/**
	 * @return the infoService
	 */
	public InfoService getInfoService() {
		return this.infoService;
	}

	/**
	 * @param infoService the infoService to set
	 */
	public void setInfoService(final InfoService infoService) {
		this.infoService = infoService;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return this.hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(final String hash) {
		this.hash = hash;
	}
}
