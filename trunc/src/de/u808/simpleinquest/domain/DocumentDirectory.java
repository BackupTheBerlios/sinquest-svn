package de.u808.simpleinquest.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DocumentDirectory {
	
	protected Long id;
	
	protected String path;
	
	protected Boolean blockDirectDownload;
	
	protected String defaultFetchPrefixUrl;
	
	protected Set<Document> documents;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column (nullable=false)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Basic
	@Column (nullable=false)
	public Boolean getBlockDirectDownload() {
		return blockDirectDownload;
	}

	public void setBlockDirectDownload(Boolean blockDirectDownload) {
		this.blockDirectDownload = blockDirectDownload;
	}

	@Basic
	@Column (nullable=true)
	public String getDefaultFetchPrefixUrl() {
		return defaultFetchPrefixUrl;
	}

	public void setDefaultFetchPrefixUrl(String defaultFetchPrefixUrl) {
		this.defaultFetchPrefixUrl = defaultFetchPrefixUrl;
	}

	@OneToMany(targetEntity=de.u808.simpleinquest.domain.Document.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER ,mappedBy="documentDirectory")
	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

}
