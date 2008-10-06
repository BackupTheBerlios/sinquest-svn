package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pdfbox.searchengine.lucene.LucenePDFDocument;

public class PDFIndexer extends AbstractIndexer{
	
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void setContentsFild(File file) {
//		Document document;
		try {
			this.document = LucenePDFDocument.getDocument(file);
			this.document = FileDocument.Document(document, file);
		} catch (IOException e) {
			log.error("Dokument " + file.toString() + " konnte nicht indexiert werden", e);
			return;
		}
	}

}
