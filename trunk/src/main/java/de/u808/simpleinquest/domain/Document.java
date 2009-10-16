/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.simpleinquest.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="type",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue(value="F")
public class Document {

	protected Long id;
	
	protected String hash;
	
	protected Date lastIndexUpdate;
	
	protected DocumentDirectory parent;
	
	protected DocumentConfig documentConfig;

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
    @JoinColumn(name="DOCUMENT_DIRECTORY_ID", nullable=true) 
	public DocumentDirectory getParent() {
		return parent;
	}

	public void setParent(DocumentDirectory documentDirectory) {
		this.parent = documentDirectory;
	}

	@Basic
	@Column (nullable=true)
	public Date getLastIndexUpdate() {
		return lastIndexUpdate;
	}

	public void setLastIndexUpdate(Date lastIndexUpdate) {
		this.lastIndexUpdate = lastIndexUpdate;
	}

	@OneToOne
	@JoinColumn(name="DOCUMENT_CONFIG_ID", nullable=true)
	public DocumentConfig getDocumentConfig() {
		return documentConfig;
	}

	public void setDocumentConfig(DocumentConfig documentConfig) {
		this.documentConfig = documentConfig;
	}
	
}
