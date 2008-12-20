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
public class Configuration {

	@Element
	private String simpleInquestHome;
	
	@Element
	private IndexerConfiguration indexerConfiguration;
	
	@Element
	private MimeIconMap mimeIconMap;

	
	@ElementList
	private List<DirectoryConfiguration> directoriesToIndex;

	public String getSimpleInquestHome() {
		return simpleInquestHome;
	}

	public void setSimpleInquestHome(String simpleInquestHome) {
		this.simpleInquestHome = simpleInquestHome;
	}

	public List<DirectoryConfiguration> getDirectoriesToIndex() {
		return directoriesToIndex;
	}

	public void setDirectoriesToIndex(
			List<DirectoryConfiguration> directoriesToIndex) {
		this.directoriesToIndex = directoriesToIndex;
	}

	public IndexerConfiguration getIndexerConfiguration() {
		return indexerConfiguration;
	}

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
