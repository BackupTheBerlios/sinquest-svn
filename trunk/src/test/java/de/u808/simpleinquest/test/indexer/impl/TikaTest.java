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

package de.u808.simpleinquest.test.indexer.impl;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;

public class TikaTest {

	@Test
	public void test(){
		try {
			//String file = "C:\\Documents and Settings\\afriedel\\My Documents\\workspace\\SimpleInquest\\TestDocuments\\Test Alpha.odt";
			//String file = "C:\\Documents and Settings\\afriedel\\My Documents\\workspace\\SimpleInquest\\TestDocuments\\Health-Care 051002.doc";
			//String file = "C:\\Documents and Settings\\afriedel\\My Documents\\workspace\\JKnowledgeMap\\Sample\\IT_Nutzwertanalyse-d.pdf";
			String file = "C:\\Users\\friedel\\workspace\\JKnowledgeMap\\Sample\\IT_Nutzwertanalyse.pdf";
			InputStream input = new FileInputStream(file); 

			Metadata metadata = new Metadata(); 
			ContentHandler handler =  new BodyContentHandler();

			new AutoDetectParser().parse(input,
					handler, metadata);

			String title = metadata.get(Metadata.TITLE);
			System.out.println("ID: " + metadata.get(Metadata.IDENTIFIER));
			String content = handler.toString();
			System.out.println(content);
			System.out.println();
			System.out.println("ID: " + metadata.get(Metadata.IDENTIFIER));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
