package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;

import de.u808.simpleinquest.indexer.ContentHandler;

public class WordContentHandler implements ContentHandler{
	
	private WordExtractor wordExtractor;

	public String extractContent(File file) throws Exception {
		try {
			//WordTextExtractorFactory fab = new WordTextExtractorFactory();
			wordExtractor = new WordExtractor(new FileInputStream(file));
			//TextExtractor extractor = fab.textExtractor();
			return wordExtractor.getText();			
		} catch (Exception e) {
			throw new IndexerException(e.getMessage(), e);
		}
	}

	public void dispose() {
		wordExtractor = null;
	}

}
