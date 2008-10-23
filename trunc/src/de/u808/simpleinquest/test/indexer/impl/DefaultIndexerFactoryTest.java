package de.u808.simpleinquest.test.indexer.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.u808.simpleinquest.indexer.IndexerFactory;

public class DefaultIndexerFactoryTest {
	
	@Test
	public void testFactory(){
		String[] paths = {"de/u808/simpleinquest/applicationContext.xml"};
        IndexerFactory indexerFactory = (IndexerFactory) new ClassPathXmlApplicationContext(paths).getBean("");
		assertNotNull(indexerFactory);
	}

}
