package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import de.u808.common.MD5Util;
import de.u808.simpleinquest.indexer.Indexer;

public class DefaultIndexer implements Indexer {

	@Override
	public Document indexFile(File file) throws FileNotFoundException {
		Document document = null;
		if (file.canRead()) {
			document = new Document();
			InputStream input = new FileInputStream(file);

			Metadata metadata = new Metadata();
			ContentHandler handler = new BodyContentHandler();

			try {
				new AutoDetectParser().parse(input, handler, metadata);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TikaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			document.add(new Field(Indexer.PATH_FIELD_NAME, file.getPath(),
					Field.Store.YES, Field.Index.UN_TOKENIZED));
			document.add(new Field(Indexer.ID_FIELD_NAME, MD5Util.getInstance()
					.getMD5Hex(file.getPath()), Field.Store.YES,
					Field.Index.UN_TOKENIZED));

			document.add(new Field(Indexer.MODIFIED_FIELD_NAME,
					DateTools.timeToString(file.lastModified(),
							DateTools.Resolution.MINUTE), Field.Store.YES,
					Field.Index.UN_TOKENIZED));

			document.add(new Field(Indexer.CONTENT_FIELD_NAME,
					new StringReader(handler.toString())));
		} else {
			// TODO log msg;
		}
		return document;
	}

}
