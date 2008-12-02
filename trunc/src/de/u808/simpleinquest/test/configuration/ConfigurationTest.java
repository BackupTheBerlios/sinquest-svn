package de.u808.simpleinquest.test.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.config.DirectoryConfiguration;
import de.u808.simpleinquest.config.IndexerConfiguration;
import de.u808.simpleinquest.config.MimeIconMap;

public class ConfigurationTest {
	
	@Test
	public void createXML(){
		Exception testE = null;
		Configuration configuration = new Configuration();
		configuration.setSimpleInquestHome("C:\\SimpleInquestHome");
		
		DirectoryConfiguration directoryConfigurationA = new DirectoryConfiguration();
		directoryConfigurationA.setBlockDirectDownload(false);
		directoryConfigurationA.setPath("C:\\Users\\friedel\\workspace\\JKnowledgeMap\\Sample");
		Map<String, String> prefixPathMapA = new LinkedHashMap<String, String>();
		List<DirectoryConfiguration> directoriesToIndex = new LinkedList<DirectoryConfiguration>();
		prefixPathMapA.put("Windows", "file://localhost/C|/Users/friedel/workspace/JKnowledgeMap/Sample");
		prefixPathMapA.put("Linux", "file://localhost/home/friedel/workspace/JKnowledgeMap/Sample");
		directoryConfigurationA.setSystemFetchPrefixUrlMap(prefixPathMapA);
		
		DirectoryConfiguration directoryConfigurationB = new DirectoryConfiguration();
		directoryConfigurationB.setBlockDirectDownload(true);
		directoryConfigurationB.setPath("C:\\SimpleInquestHome");
		Map<String, String> prefixPathMapB = new LinkedHashMap<String, String>();
		prefixPathMapB.put("Windows", "file://localhost/C|/Users/friedel/workspace/JKnowledgeMap/Sample");
		prefixPathMapB.put("Linux", "file://localhost/home/friedel/workspace/JKnowledgeMap/Sample");
		directoryConfigurationB.setSystemFetchPrefixUrlMap(prefixPathMapA);
		
		directoriesToIndex.add(directoryConfigurationA);
		directoriesToIndex.add(directoryConfigurationB);
		configuration.setDirectoriesToIndex(directoriesToIndex);
		MimeIconMap mimeIconMap = new MimeIconMap();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("html", "bla.gif");
		map.put("pdf", "blub.gif");
		mimeIconMap.setMap(map);
		configuration.setMimeIconMap(mimeIconMap);
		
		IndexerConfiguration indexerConfiguration = new IndexerConfiguration();
		indexerConfiguration.setIndexSearchRefreshCount(50);
		Map<String, String> testMimetypeMapping = new LinkedHashMap<String, String>();
		testMimetypeMapping.put("application/pdf", "de.ex.PDFIndexer");
		testMimetypeMapping.put("application/msword", "de.exe.MSWordIndexer");
		indexerConfiguration.setMimeTypeIndexerMap(testMimetypeMapping);
		configuration.setIndexerConfiguration(indexerConfiguration);
		
		Serializer serializer = new Persister();
		File result = new File("SimpleInquestConf.xml");

		try {
			serializer.write(configuration, result);
		} catch (Exception e) {
			testE = e;
			e.printStackTrace();
		}
		assertNull(testE);
	}
	
	@Test
	public void readXML(){
		Exception testE = null;
		Serializer serializer = new Persister();
		File configFile = new File("SimpleInquestConf.xml");
		try {
			Configuration conf = serializer.read(Configuration.class, configFile);
			IndexerConfiguration indexerConf = conf.getIndexerConfiguration();
			assertNotNull(indexerConf);
			assertNotNull(indexerConf.getMimeTypeIndexerMap());
			assertTrue(!indexerConf.getMimeTypeIndexerMap().keySet().isEmpty());
		} catch (Exception e) {
			testE = e;
			e.printStackTrace();
		}
		assertNull(testE);
	}

}
