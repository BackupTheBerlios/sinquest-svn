package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;
import de.u808.simpleinquest.util.FileProcessor;

public class UpdateIndexFileProcessor implements FileProcessor{
	
	private static Log log = LogFactory.getLog(UpdateIndexFileProcessor.class);
	private IndexSearcher indexSearcher;
	private Directory indexDirectory;
	//TODO Spring Bean
	private IndexerFactory indexerFactory;
	
	private List<File> newFiles = new LinkedList<File>();
	private List<File> modifiedFiles = new LinkedList<File>();
	private List<File> deletedFiles = new LinkedList<File>();
	
	private boolean newIndex = false;
	
	public UpdateIndexFileProcessor (){
		try {			
			//TODO Spring bean
			indexerFactory = DefaultIndexerFactory.getInstance();
			indexDirectory = FSDirectory.getDirectory(SystemConfig.getInstance().getIndexDirectory());
			//
			if(IndexReader.indexExists(indexDirectory)){
				indexSearcher = new IndexSearcher(indexDirectory);
			}
			else{
				newIndex = true;
			}
		} catch (CorruptIndexException e) {
			log.error("Index corrupted", e);
		} catch (IOException e) {
			log.error("IOException while opening index", e);
		}
	}

	public void processFile(File file) {
		//TODO Fileliste aus dem Index laden
		//Listen vergleichen
		//File in die entsprechenden Listen sortieren
		if(file != null && file.canRead() && !file.isHidden()){
			TermQuery query = new TermQuery(new Term(Indexer.PATH_FIELD_NAME, file.getPath()));
			Hits hits = null;
			try {
				if(!newIndex){
					hits = this.indexSearcher.search(query);
				}
				if(hits != null && hits.length()>0){
					Document doc = hits.doc(0);
					String directoryList = doc.getField(Indexer.DIRECTORY_LIST_FIELD_NAME).stringValue();
					this.processDirectoryList(file, directoryList);
				}
				else {
					//New dir, add all
					this.newFiles.add(file);
					File[] files = file.listFiles();
					for(File f : files){
						if(f.isFile()){
							this.newFiles.add(f);
						}
					}
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			log.info("Ignoring file: " + file.getPath());
		}
	}
	
	private void processDirectoryList(File file, String storedDirectoryList) throws IOException{
		if(StringUtils.isNotEmpty(storedDirectoryList)){
			String[] fileNames = storedDirectoryList.split(";");
			Set<String> fileSet = new HashSet<String>();
			for(String s : fileNames){
				fileSet.add(s);
				File fileToCheck = new File(file, s);
				if(fileToCheck.exists()){
					if(this.isDocumentIndexedAndModified(fileToCheck, new Term(Indexer.PATH_FIELD_NAME, fileToCheck.getPath()))){
						this.modifiedFiles.add(fileToCheck);
					}
				}
				else{
					log.debug("File " + file.getPath() +" was deleted or moved");
					this.deletedFiles.add(fileToCheck);
				}
			}
			//check for new
			String[] actuelFileList = file.list();
			for(String s : actuelFileList){
				if(!fileSet.contains(s)){
					//File is new
					log.debug("New File : " + s);
					this.newFiles.add(new File(file, s));
				}
			}
		}
	}
	
	private void indexFile(File file) throws IOException{
		if(file.canRead()){
			String[] fileList = file.list();
		}
	}
	
	private boolean isDocumentIndexedAndModified(File file, Term uidTerm) throws IOException{
		TermQuery query = new TermQuery(uidTerm);
		Hits hits = this.indexSearcher.search(query);
		if(hits.length()>0){
			Document doc = hits.doc(0);
			Date lastModified = new Date(DateTools.round(file.lastModified(), DateTools.Resolution.MINUTE)) ;
			Date storedDate;
			try {
				storedDate = DateTools.stringToDate(doc.getField(Indexer.MODIFIED_FIELD_NAME).stringValue());
				if(lastModified.after(storedDate)){
					log.info("File changed " + file.getAbsolutePath());
					return true;
				}
				else{
					log.info("File not changed " + file.getAbsolutePath());
					return false;
				}
			} catch (ParseException e) {
				log.error("Date pare Exception", e);
				//zur sicherheit neu indizieren
				return true;
			}
		}
		else{
			return false;
		}
	}
	
	private void deleteDocuments(List<File> files) throws CorruptIndexException, IOException{
		if(!files.isEmpty() && IndexReader.indexExists(indexDirectory)){
			IndexReader indexReader = IndexReader.open(indexDirectory);
			for(File file: files){
				Term uidTerm = new Term(Indexer.PATH_FIELD_NAME, file.getPath());
				indexReader.deleteDocuments(uidTerm);
			}
			indexReader.close();
		}
		else{
			log.info("Nothing to delete or index does not exist");
		}
	}
	
	private void indexDocuments(List<File> files) throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter indexWriter = new IndexWriter(indexDirectory, new StandardAnalyzer());
		for(File file: files){
			if(file.isDirectory()){
				Document doc = DirectoryDocument.Document(file);
				indexWriter.addDocument(doc);
			}
			else {
				Indexer indexer = indexerFactory.getIndexer(file);
				if(indexer != null){
					Document document = indexer.indexFile(file);
					if(document != null){
						log.info("Indexing file: " + file.getPath());
						indexWriter.addDocument(indexer.indexFile(file));
					}
					else{
						log.warn("Indexer " + indexer.getClass() + " returned no content to index");
					}
				}
				else{
					log.debug("No indexer for file: " + file.getPath());
				}
			}
		}
		indexWriter.close(true);
	}

	public void dispose() {
		//TODO 1. delete deleted and modified files with IndexReader
		//     2. index modified and new files with IndexWriter
		//     3. set new IndesSearcher in Bean
		try {
			//1.
			List<File> documentsToDelete = new LinkedList<File>();
			documentsToDelete.addAll(this.deletedFiles);
			documentsToDelete.addAll(this.modifiedFiles);
			this.deleteDocuments(documentsToDelete);
			//2.
			List<File> documentsToIndex = new LinkedList<File>();
			documentsToIndex.addAll(this.newFiles);
			documentsToIndex.addAll(this.modifiedFiles);
			this.indexDocuments(documentsToIndex);
			if(indexSearcher != null){
				this.indexSearcher.close();
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
