package de.u808.simpleinquest.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

@Root
public class Configuration {

	@Element
	private String simpleInquestHome;
	
	@Element
	private IndexerConfiguration indexerConfiguration;
	
	@Element
	private MimeIconMap mimeIconMap;

	
	@ElementArray(entry="directoryToIndex")
	private String[] directoriesToIndexList;

	public String getSimpleInquestHome() {
		return simpleInquestHome;
	}

	public void setSimpleInquestHome(String simpleInquestHome) {
		this.simpleInquestHome = simpleInquestHome;
	}

	public String[] getDirectoriesToIndexList() {
		return directoriesToIndexList;
	}

	public void setDirectoriesToIndexList(String[] directoriesToIndexList) {
		this.directoriesToIndexList = directoriesToIndexList;
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
		for(String dir : directoriesToIndexList){
			stringBuilder.append("Index directory: ");
			stringBuilder.append(dir);
			stringBuilder.append("; ");
		}
		return stringBuilder.toString();
	}
}
