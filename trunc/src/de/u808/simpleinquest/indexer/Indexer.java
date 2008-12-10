package de.u808.simpleinquest.indexer;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.lucene.document.Document;
import org.apache.tika.metadata.Metadata;

import de.u808.simpleinquest.indexer.impl.IndexerException;

public interface Indexer {
	
	public static String CONTENT_FIELD_NAME = "contents";
	public static String PATH_FIELD_NAME = "path";
	public static String ID_FIELD_NAME = "doc-ID";
	public static String MODIFIED_FIELD_NAME = Metadata.LAST_MODIFIED;
	public static String DIRECTORY_LIST_FIELD_NAME = "directoryList";
	
	public static String AUTOR_FIELD_NAME = Metadata.AUTHOR;
	public static String TITLE_FIELD_NAME = Metadata.AUTHOR;

	public Document indexFile(File file) throws FileNotFoundException, IndexerException;
}
