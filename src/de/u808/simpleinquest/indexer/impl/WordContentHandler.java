package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;

import org.textmining.extraction.TextExtractor;
import org.textmining.extraction.word.WordTextExtractorFactory;

import de.u808.simpleinquest.indexer.ContentHandler;

public class WordContentHandler implements ContentHandler{

	@Override
	public String extractContent(File file) throws Exception {
		WordTextExtractorFactory fab = new WordTextExtractorFactory();
		TextExtractor extractor = fab.textExtractor(new FileInputStream(file));
		return extractor.getText();
	}

}
