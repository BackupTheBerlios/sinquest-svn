package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.pdfbox.searchengine.lucene.LucenePDFDocument;

import de.u808.simpleinquest.indexer.Indexer;

public class PDFIndexer extends AbstractIndexer{
	
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void setContentsFild(File file) {
		Document document;
		try {
			document = LucenePDFDocument.getDocument(file);
			if(document != null && document.getField(Indexer.CONTENT_FIELD_NAME) != null){
				this.document.add(document.getField(Indexer.CONTENT_FIELD_NAME));
				this.document.add(document.getField(Indexer.AUTOR_FIELD_NAME));
				this.document.add(document.getField(Indexer.TITLE_FIELD_NAME));
			}
		} catch (IOException e) {
			log.error("Dokument " + file.toString() + " konnte nicht indexiert werden", e);
			return;
		}
	}

}
