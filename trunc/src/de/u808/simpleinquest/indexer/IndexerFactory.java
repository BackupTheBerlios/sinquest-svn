package de.u808.simpleinquest.indexer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IndexerFactory {
	
	public Indexer getIndexer(File file) throws IOException;
	
	public Collection<String> getMappedMimeTypes();

}
