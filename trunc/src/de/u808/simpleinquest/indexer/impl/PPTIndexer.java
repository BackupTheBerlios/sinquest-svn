package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.StringReader;

import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;

public class PPTIndexer extends AbstractIndexer{
	
	private PPTContentHandler pptContentHandler = new PPTContentHandler();

	@Override
	public void setContentsFild(File file) throws IndexerException {
		try {
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, new StringReader(pptContentHandler.extractContent(file))));
		} catch (Exception e) {
			throw new IndexerException(e.getMessage(), e);
		}
	}

}
