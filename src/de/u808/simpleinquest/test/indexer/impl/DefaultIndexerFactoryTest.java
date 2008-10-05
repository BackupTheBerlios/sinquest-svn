package de.u808.simpleinquest.test.indexer.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.u808.simpleinquest.indexer.impl.DefaultIndexerFactory;

public class DefaultIndexerFactoryTest {
	
	@Test
	public void testFactory(){
		DefaultIndexerFactory factory = DefaultIndexerFactory.getInstance();
		assertNotNull(factory);
	}

}
