package de.u808.simpleinquest.test.configuration;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.config.IndexerConfiguration;
import de.u808.simpleinquest.config.MimeIconMap;

public class ConfigurationTest {
	
	@Test
	public void createXML(){
		Exception testE = null;
		Configuration configuration = new Configuration();
		configuration.setSimpleInquestHome("C:\\SimpleInquestHome");
		String[] dirs = {"C:\\Users\\friedel\\workspace\\JKnowledgeMap\\Sample", "C:\\SimpleInquestHome"};
		configuration.setDirectoriesToIndexList(dirs);
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
