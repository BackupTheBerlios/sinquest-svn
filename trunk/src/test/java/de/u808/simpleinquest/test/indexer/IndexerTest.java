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

package de.u808.simpleinquest.test.indexer;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.lucene.document.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"}) 
public class IndexerTest {
	
	@Autowired
	@Qualifier("indexerFactory")
	IndexerFactory indexerFactory;
	
	@Test
	public void testIndexer(){
		//TODO Fix it
		//IndexerFactory indexerFactory = DefaultIndexerFactory.getInstance();
		File sampleDir = new File("src/test/resources/testdata");
		try {
			//File fileToIndex = new File(sampleDir, "Geboren_vor_1978.pps");
			//File fileToIndex = new File(sampleDir, "testOO2.odt");
			File fileToIndex = new File(sampleDir, "TestAlpha.java");
			assertNotNull("File to index is null", fileToIndex);
			assertTrue("File to index does not exist", fileToIndex.exists());
			assertNotNull("IndexerFactory is null", indexerFactory);
			Indexer indexer = indexerFactory.getIndexer(fileToIndex);
			assertNotNull(indexer);
			Document document = indexer.indexFile(fileToIndex);
			assertNotNull(document);
			assertNotNull(document.getField(Indexer.CONTENT_FIELD_NAME));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
