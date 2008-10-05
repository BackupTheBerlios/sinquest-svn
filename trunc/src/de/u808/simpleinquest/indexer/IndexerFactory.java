package de.u808.simpleinquest.indexer;

import java.io.File;
import java.io.IOException;

public interface IndexerFactory {
	
	public Indexer getIndexer(File file) throws IOException;

}
