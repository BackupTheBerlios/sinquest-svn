package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import org.apache.lucene.document.Field;
import org.textmining.extraction.excel.ExcelTextExtractor;

import de.u808.simpleinquest.indexer.Indexer;

public class ExcelIndexer extends AbstractIndexer {

	@Override
	public void setContentsFild(File file) throws Exception {
		try {
			ExcelTextExtractor excelTextExtractor = new ExcelTextExtractor(new FileInputStream(this.file));
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, new StringReader(excelTextExtractor.getText())));
		} catch (Exception e){
			throw new IndexerException(e.getMessage(), e);
		}
		
	}

}
