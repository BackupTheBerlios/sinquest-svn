package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.lucene.document.Field;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import de.u808.simpleinquest.indexer.Indexer;

public class ExcelIndexer extends AbstractIndexer {

	@Override
	public void setContentsFild(File file) throws Exception {
		try {
			InputStream inp = new FileInputStream(this.file);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
			ExcelExtractor extractor = new ExcelExtractor(wb);

			extractor.setFormulasNotResults(true);
			extractor.setIncludeSheetNames(true);

			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, new StringReader(extractor.getText())));
		} catch (Exception e){
			throw new IndexerException(e.getMessage(), e);
		}
		
	}

}
