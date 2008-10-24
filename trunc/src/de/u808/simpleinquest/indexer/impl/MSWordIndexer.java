package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;
import org.textmining.extraction.excel.ExcelTextExtractor;

import de.u808.simpleinquest.indexer.Indexer;
import eu.medsea.util.MimeUtil;

public class MSWordIndexer extends AbstractIndexer {
	
	Log log = LogFactory.getLog(this.getClass());
	
	private PPTContentHandler pptContentHandler = new PPTContentHandler();
	private WordContentHandler wordContentHandler = new WordContentHandler();
	
    private String getContents() throws Exception {
        String contents = "";
        String fileExtention = MimeUtil.getFileExtension(this.file);
        if("ppt".equals(fileExtention) || "pps".equals(fileExtention)){
        	contents = pptContentHandler.extractContent(file);
        }
        else if("doc".equals(fileExtention)){
        	contents = wordContentHandler.extractContent(file);
        }
        else if("xls".equals(fileExtention)){
        	ExcelTextExtractor excelTextExtractor = new ExcelTextExtractor(new FileInputStream(this.file));
        	contents = excelTextExtractor.getText();
        }
        else{
        	log.error("Unsupported MSWord document. Document can´t be indexed");
        }
        return contents;
    }

	@Override
	public void setContentsFild(File file) {
		try {
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, new StringReader(getContents())));
		} catch (Exception e) {
			log.error("Error while indexing " + file.getName(), e);
		}
	}
}
