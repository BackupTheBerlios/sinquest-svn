package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import de.u808.common.MD5Util;
import de.u808.simpleinquest.indexer.Indexer;

public class DefaultIndexer implements Indexer {

	private static Log log = LogFactory.getLog(DefaultIndexer.class);

	@Override
	public Document indexFile(File file) throws FileNotFoundException,
			IndexerException {
		Document document = null;
		if (file.canRead()) {
			try {
				document = new Document();
				InputStream input = new FileInputStream(file);

				Metadata metadata = new Metadata();
				ContentHandler handler = new BodyContentHandler();
				new AutoDetectParser().parse(input, handler, metadata);

				document.add(new Field(Indexer.PATH_FIELD_NAME, file.getPath(),
						Field.Store.YES, Field.Index.UN_TOKENIZED));
				document.add(new Field(Indexer.ID_FIELD_NAME, MD5Util
						.getInstance().getMD5Hex(file.getPath()),
						Field.Store.YES, Field.Index.UN_TOKENIZED));

				document.add(new Field(Indexer.MODIFIED_FIELD_NAME, DateTools
						.timeToString(file.lastModified(),
								DateTools.Resolution.MINUTE), Field.Store.YES,
						Field.Index.UN_TOKENIZED));

				document.add(new Field(Indexer.CONTENT_FIELD_NAME,
						new StringReader(handler.toString())));
			} catch (Exception e) {
				if(e instanceof TikaException){
					Throwable t = e.getCause();
					if(t != null && t.getMessage() != null){
						if (t.getMessage().startsWith("Error decrypting document")){
							log.debug("Can´t index encrypted document.");
							return document;
						}
					}
				}
				throw new IndexerException(e.getMessage(), e);
			}
		} else {
			log.debug("Can´t read file: " + file.getName());
		}
		return document;
	}

}
