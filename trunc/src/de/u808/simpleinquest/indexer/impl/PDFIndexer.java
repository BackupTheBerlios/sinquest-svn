package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import de.u808.simpleinquest.indexer.Indexer;

public class PDFIndexer extends AbstractIndexer{
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private PDFTextStripper pdfStripper = null;
	private PDDocument doc = null; 

	@Override
	public void setContentsFild(File file) throws IndexerException {
		try {
			doc = PDDocument.load(file);
			pdfStripper = new PDFTextStripper();
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, pdfStripper.getText(doc), Store.NO, Index.TOKENIZED));
			doc.close();
		} catch (IOException e) {
			String msg = "Dokument " + file.toString() + " konnte nicht indexiert werden. Ursache: " + e.getMessage(); 
			throw new IndexerException(msg, e);
		}
		finally {
			doc = null;
			pdfStripper = null;
		}
	}

}
