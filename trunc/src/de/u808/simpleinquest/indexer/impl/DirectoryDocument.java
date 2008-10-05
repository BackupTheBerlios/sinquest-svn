package de.u808.simpleinquest.indexer.impl;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;

public class DirectoryDocument extends FileDocument {

	private DirectoryDocument() {
		super();
	}

	public static Document Document(File f)
			throws java.io.FileNotFoundException {
		Document doc = FileDocument.Document(f);
		StringBuilder stringBuilder = new StringBuilder();
		if(f != null && f.canRead()){
			String[] fileList = f.list();
			for(String fileName : fileList){
				stringBuilder.append(fileName);
				stringBuilder.append(";");
			}
		}
		doc.add(new Field(Indexer.DIRECTORY_LIST_FIELD_NAME, stringBuilder.toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
		return doc;
	}
}
