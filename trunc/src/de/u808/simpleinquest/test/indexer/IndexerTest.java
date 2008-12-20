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

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.apache.lucene.document.Document;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;

public class IndexerTest {
	
	private static IndexerFactory indexerFactory;
	
	@BeforeClass
	public static void initConfig(){
		File contextFile = new File("WebContent/WEB-INF/applicationContext.xml");
		Resource res = new FileSystemResource(contextFile);
		XmlBeanFactory factory = new XmlBeanFactory(res);
		indexerFactory = (IndexerFactory) factory.getBean("indexerFactory");
	}
	
	@Test
	public void testIndexer(){
		//TODO Fix it
		//IndexerFactory indexerFactory = DefaultIndexerFactory.getInstance();
		File sampleDir = new File("C:/Users/friedel/workspace/JKnowledgeMap/Sample");
		try {
			//File fileToIndex = new File(sampleDir, "Geboren_vor_1978.pps");
			//File fileToIndex = new File(sampleDir, "testOO2.odt");
			File fileToIndex = new File(sampleDir, "TestAlpha.java");
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
