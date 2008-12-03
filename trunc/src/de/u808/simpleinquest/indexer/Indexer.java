package de.u808.simpleinquest.indexer;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.lucene.document.Document;

public interface Indexer {
	
	public static String CONTENT_FIELD_NAME = "contents";
	public static String PATH_FIELD_NAME = "path";
	public static String FILE_FETCH_PREFIX_FIELD_NAME = "fileFetchPrefix";
	public static String PREVENT_DIRECT_DOWNLOAD_FIELD_NAME = "preventDirectDownload";
	public static String ID_FIELD_NAME = "doc-ID";
	public static String MODIFIED_FIELD_NAME = "modified";
	public static String DIRECTORY_LIST_FIELD_NAME = "directoryList";
	
	public static String AUTOR_FIELD_NAME = "Author";
	public static String TITLE_FIELD_NAME = "Title";

	public Document indexFile(File file) throws FileNotFoundException;
	
	//TODO Felder werden bei PDF´s nicht gesetzt! JSP Anzeige stimmt nicht
	//public Document indexFile(File file, DirectoryConfiguration directoryConfiguration) throws FileNotFoundException;
}
