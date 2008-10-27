package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileReader;

import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;

public class PlainTextIndexer extends AbstractIndexer {

	@Override
	public void setContentsFild(File file) throws Exception {
		FileReader fileReader = new FileReader(file);
		this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, fileReader));
	}

}
