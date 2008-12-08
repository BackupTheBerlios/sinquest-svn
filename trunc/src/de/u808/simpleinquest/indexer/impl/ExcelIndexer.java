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
	
	private InputStream inp;
	private HSSFWorkbook wb;
	private ExcelExtractor extractor;
	private StringReader stringReader;

	@Override
	public void setContentsFild(File file) throws Exception {
		try {
			this.inp = new FileInputStream(this.file);
			this.wb = new HSSFWorkbook(new POIFSFileSystem(inp));
			this.extractor = new ExcelExtractor(wb);

			this.extractor.setFormulasNotResults(true);
			this.extractor.setIncludeSheetNames(true);

			this.stringReader = new StringReader(extractor.getText());
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, stringReader));
		} catch (Exception e){
			throw new IndexerException(e.getMessage(), e);
		} finally {
			if(inp != null){
				inp.close();
			}
			wb = null;
			extractor = null;
		}
		
	}

}
