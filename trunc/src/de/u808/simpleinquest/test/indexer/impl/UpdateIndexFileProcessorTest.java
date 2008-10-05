package de.u808.simpleinquest.test.indexer.impl;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;

import static org.junit.Assert.assertNull;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.impl.UpdateIndexFileProcessor;
import de.u808.simpleinquest.util.DirectoryTraverser;
import de.u808.simpleinquest.util.InvalidArgumentException;

public class UpdateIndexFileProcessorTest {
	
	@BeforeClass
	public static void init(){
		Serializer serializer = new Persister();
		Configuration conf;
		try {
			conf = serializer.read(Configuration.class, new File("./WebContent/WEB-INF/SimpleInquestConf.xml"));
			new SystemConfig(conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate(){
		Exception exception = null;
		String rootDir = "C:/Users/friedel/workspace/JKnowledgeMap/Sample";
		try {
			new DirectoryTraverser(new File(rootDir), new UpdateIndexFileProcessor(), DirectoryTraverser.TraversalMode.JustDirectories).startTraversal();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
			exception = e;
		}
		assertNull(exception);
	}

}
