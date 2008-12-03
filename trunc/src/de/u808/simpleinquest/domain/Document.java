package de.u808.simpleinquest.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Document {

	protected Long id;
	
	protected String hash;
	
	protected DocumentDirectory documentDirectory;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column (nullable=false)
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@ManyToOne
    @JoinColumn(name="DOCUMENT_DIRECTORY_ID", nullable=false) 
	public DocumentDirectory getDocumentDirectory() {
		return documentDirectory;
	}

	public void setDocumentDirectory(DocumentDirectory documentDirectory) {
		this.documentDirectory = documentDirectory;
	}
	
}
