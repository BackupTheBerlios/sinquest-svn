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

package de.u808.simpleinquest.config;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
/**
 * This class is used to handle the XML configuration file SimpleInquestConf.xml
 */
public class Configuration {

	@Element
	private String simpleInquestHome;
	
	@Element
	private IndexerConfiguration indexerConfiguration;
	
	@Element
	private MimeIconMap mimeIconMap;

	
	@ElementList
	private List<DirectoryConfiguration> directoriesToIndex;

	/**
	 * Getter for the SimpleInquestHome.
	 * @return The root directory for the database and index files.
	 */
	public String getSimpleInquestHome() {
		return simpleInquestHome;
	}

	/**
	 * Setter for the SimpleInquestHome.
	 * @param simpleInquestHome
	 */
	public void setSimpleInquestHome(String simpleInquestHome) {
		this.simpleInquestHome = simpleInquestHome;
	}

	/**
	 * @return A list of all DirectoryConfigurations
	 * @see de.u808.simpleinquest.config.DirectoryConfiguration
	 */
	public List<DirectoryConfiguration> getDirectoriesToIndex() {
		return directoriesToIndex;
	}

	/**
	 * @param directoriesToIndex The DirectoryConfigurations for the IndexUpdater.
	 */
	public void setDirectoriesToIndex(
			List<DirectoryConfiguration> directoriesToIndex) {
		this.directoriesToIndex = directoriesToIndex;
	}

	/**
	 * Default getter for the IndexerConfiguration.
	 * @return The actual configuration.
	 */
	public IndexerConfiguration getIndexerConfiguration() {
		return indexerConfiguration;
	}

	/**
	 * @param indexerConfiguration
	 */
	public void setIndexerConfiguration(IndexerConfiguration indexerConfiguration) {
		this.indexerConfiguration = indexerConfiguration;
	}
	
	public MimeIconMap getMimeIconMap() {
		return mimeIconMap;
	}

	public void setMimeIconMap(MimeIconMap mimeIconMap) {
		this.mimeIconMap = mimeIconMap;
	}

	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SimpleInquestHome: ");
		stringBuilder.append(simpleInquestHome);
		stringBuilder.append("; ");
		for(DirectoryConfiguration dirConfig : directoriesToIndex){
			stringBuilder.append("Index directory: ");
			stringBuilder.append(dirConfig.getPath());
			stringBuilder.append("; ");
		}
		return stringBuilder.toString();
	}
}
