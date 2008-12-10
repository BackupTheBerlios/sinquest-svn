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
			String file = "C:\\Documents and Settings\\afriedel\\My Documents\\workspace\\JKnowledgeMap\\Sample\\IT_Nutzwertanalyse.pdf";
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
