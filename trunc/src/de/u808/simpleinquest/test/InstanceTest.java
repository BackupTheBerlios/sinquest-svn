package de.u808.simpleinquest.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.impl.MSWordIndexer;

public class InstanceTest {

	@Test
	public void test(){
		MSWordIndexer indexer = new MSWordIndexer();
		assertTrue(indexer instanceof Indexer);
		
		try {
			Object o = Class.forName("de.u808.simpleinquest.indexer.impl.WSWordIndexer");
			assertTrue(o.getClass().isInstance(Class.forName("de.u808.simpleinquest.indexer.Indexer")));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
