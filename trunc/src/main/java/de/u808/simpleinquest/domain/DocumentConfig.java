package de.u808.simpleinquest.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DocumentConfig {
	
	private String fetchPrefix;
	
	private boolean blockDirectDownload;

	private Long id;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	public String getFetchPrefix() {
		return fetchPrefix;
	}

	public void setFetchPrefix(String fetchPrefix) {
		this.fetchPrefix = fetchPrefix;
	}

	@Basic
	public boolean isBlockDirectDownload() {
		return blockDirectDownload;
	}

	public void setBlockDirectDownload(boolean blockDirectDownload) {
		this.blockDirectDownload = blockDirectDownload;
	}

	
}
