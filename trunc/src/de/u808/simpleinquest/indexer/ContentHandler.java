package de.u808.simpleinquest.indexer;

import java.io.File;

public interface ContentHandler {
	
	String extractContent(File file) throws Exception;

}
