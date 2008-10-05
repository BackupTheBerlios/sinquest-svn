package de.u808.simpleinquest.indexer;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.lucene.document.Document;

public interface Indexer {
	
	public static String CONTENT_FIELD_NAME = "contents";
	public static String PATH_FIELD_NAME = "path";
	public static String MODIFIED_FIELD_NAME = "modified";
	public static String DIRECTORY_LIST_FIELD_NAME = "directoryList";
	
	public static String AUTOR_FIELD_NAME = "Author";
	public static String TITLE_FIELD_NAME = "Title";

	public Document indexFile(File file) throws FileNotFoundException;
}
