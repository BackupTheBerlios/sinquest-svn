package de.u808.simpleinquest.test.indexer;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;
import de.u808.simpleinquest.indexer.impl.DefaultIndexerFactory;

public class IndexerTest {
	
	@BeforeClass
	public static void initConfig(){
		File configFile = new File("./WebContent/WEB-INF/SimpleInquestConf.xml");
		try {
			Serializer serializer = new Persister();

			Configuration conf = serializer.read(Configuration.class, configFile);
			new SystemConfig(conf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void testIndexer(){
//		//TODO Fix it
//		//IndexerFactory indexerFactory = DefaultIndexerFactory.getInstance();
//		File sampleDir = new File("C:/Users/friedel/workspace/JKnowledgeMap/Sample");
//		try {
//			//File fileToIndex = new File(sampleDir, "Geboren_vor_1978.pps");
//			File fileToIndex = new File(sampleDir, "testOO2.odt");
//			Indexer indexer = indexerFactory.getIndexer(fileToIndex);
//			assertNotNull(indexer);
//			Document document = indexer.indexFile(fileToIndex);
//			assertNotNull(document);
//			assertNotNull(document.getField(Indexer.CONTENT_FIELD_NAME));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
