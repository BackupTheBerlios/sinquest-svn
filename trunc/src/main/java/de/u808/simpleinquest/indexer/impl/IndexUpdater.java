/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import org.quartz.JobExecutionContext;

import de.u808.common.Constants;
import de.u808.common.GlobalSearchCache;
import de.u808.common.SessionSearchCache;
import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;
import de.u808.simpleinquest.service.MimeTypeRegistry;
import de.u808.simpleinquest.service.search.IndexSearchBean;
import de.u808.simpleinquest.util.FileProcessor;
import de.u808.simpleinquest.web.ConfigBeanResource;

public class IndexUpdater implements FileProcessor{
	
	private static Log log = LogFactory.getLog(IndexUpdater.class);
	private IndexSearcher indexSearcher;
	private IndexSearchBean indexSearchBean;
	private Directory indexDirectory;
	private IndexerFactory indexerFactory;
	private GlobalSearchCache globalSearchCache;
	private SessionSearchCache sessionSearchCache;
	
	private MimeTypeRegistry mimeTypeRegistry;
	
	private List<File> newFiles = new LinkedList<File>();
	private List<File> modifiedFiles = new LinkedList<File>();
	private List<File> deletedFiles = new LinkedList<File>();
	
	private String statusMessage;
	
	private int fileCount = 0;
	private int refreschLimit;
	
	private DecimalFormat memoryFormater = new DecimalFormat("#,###,##0.00");
	
	private JobExecutionContext jobExecutionContext;
	
	private boolean newIndex = false;
	
	public IndexUpdater (ConfigBeanResource configBeanResource){
		try {
			this.refreschLimit = configBeanResource.getSystemConfig().getConfiguration().getIndexerConfiguration().getIndexSearchRefreshCount();
			indexDirectory = FSDirectory.getDirectory(configBeanResource.getSystemConfig().getIndexDirectory());
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
					this.addToNewFilesList(file);
					File[] files = file.listFiles();
					if(files != null){
						for(File f : files){
							if(f.isFile()){
								this.addToNewFilesList(f);
							}
						}
					}
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
		else{
			log.info("Ignoring file: " + file.getPath());
		}
	}
	
	private void processDirectoryList(File file, String storedDirectoryList) throws IOException{
		if(StringUtils.isNotEmpty(storedDirectoryList)){
			String[] fileNames = storedDirectoryList.split(Constants.SEMICOLON);
			Set<String> fileSet = new HashSet<String>();
			for(String s : fileNames){
				fileSet.add(s);
				File fileToCheck = new File(file, s);
				if(fileToCheck.exists()){
					if(this.isDocumentIndexedAndModified(fileToCheck, new Term(Indexer.PATH_FIELD_NAME, fileToCheck.getPath()))){
						this.addTomodifiedFiles(fileToCheck);						
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
					this.addToNewFilesList(new File(file, s));					
				}
			}
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
			this.setStatusMessage("Removing deleted files from the index");
			IndexReader indexReader = IndexReader.open(indexDirectory);
			for(File file: files){
				Term uidTerm = new Term(Indexer.PATH_FIELD_NAME, file.getPath());
				indexReader.deleteDocuments(uidTerm);
			}
			indexReader.close();
			this.setStatusMessage("All deleted files removed from index");
		}
		else{
			log.info("Nothing to delete or index does not exist");
		}
	}
	
	private void indexDocuments(List<File> files) throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriter indexWriter = new IndexWriter(indexDirectory, new StandardAnalyzer());
		Iterator<File> iterator = files.iterator();
		while (iterator.hasNext()) {
			File file = (File) iterator.next();					
			if(file.isDirectory()){
				Document doc = DirectoryDocument.Document(file);
				indexWriter.addDocument(doc);
			}
			else {
				Indexer indexer = indexerFactory.getIndexer(file);
				if(indexer != null){
					Document document = null;
					try {
						log.debug("Memory before indexing in MB (M: " + memoryFormater.format(Runtime.getRuntime().maxMemory()/(1024*1024)) + " T: " + memoryFormater.format(Runtime.getRuntime().totalMemory()/(1024*1024)) + "F: " + memoryFormater.format(Runtime.getRuntime().freeMemory()/(1024*1024)) + ")");
						this.ensureEnoughHeapMemory();
						String msg = "Indexing file: " + file.getPath();
						document = indexer.indexFile(file);
						this.setStatusMessage(msg);
						log.info(msg);
						log.debug("Memory after indexing in MB (M: " + memoryFormater.format(Runtime.getRuntime().maxMemory()/(1024*1024)) + " T: " + memoryFormater.format(Runtime.getRuntime().totalMemory()/(1024*1024)) + " F: " + memoryFormater.format(Runtime.getRuntime().freeMemory()/(1024*1024)) + ")");
						iterator.remove();
					} catch (IndexerException e) {
						log.error("Error during indexing", e);
					} catch (OutOfMemoryError outOfMemoryError) {
						log.warn("File seems to be to big for the actual free heap. Try to increase availible memory with vm option -Xmx if this is a recurring error message");
						log.info("Try to free memory");
						document = null;
						System.gc();
						this.refreschIndex();
					}
					if(document != null){												
						indexWriter.addDocument(document);
					}
					else{
						String msg = "Indexer " + indexer.getClass() + " returned no content to index";
						this.setStatusMessage(msg);
						log.warn(msg);
					}
				}
				else{
					log.debug("No indexer for file: " + file.getPath());
				}
			}
		}
		String msg = "Optimizing index";
		this.setStatusMessage(msg);
		log.info(msg);
		indexWriter.flush();
		indexWriter.optimize();
		msg = "Index optimized";
		this.setStatusMessage(msg);
		log.info(msg);
		indexWriter.close(true);
		indexWriter = null;
	}

	public void dispose() {
		this.refreschIndex();
		this.setStatusMessage("Idle");
	}
	
	private void refreschIndex(){
		// 1. delete deleted and modified files with IndexReader
		// 2. index modified and new files with IndexWriter
		// 3. set new IndesSearcher in Bean
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
			//clear local Lists
			this.newFiles.clear();
			this.modifiedFiles.clear();
			this.deletedFiles.clear();
			//3.
			if(indexSearcher != null){
				this.indexSearcher.close();
			}
			//set new IndexSearcher
			this.performIndexSearchUpdate();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void addToNewFilesList(File file){
		if(file.isDirectory()) {
			this.newFiles.add(file);
		}
		else {
			String mimeType = mimeTypeRegistry.getMimeType(file);
			if(this.indexerFactory.getMappedMimeTypes().contains(mimeType)){
				this.newFiles.add(file);
				this.checkUpdateCondition();
			}
			else{
				log.debug("No indexer for file: " + file.getPath());
			}
		}
	}
	
	private void addTomodifiedFiles(File file){
		if(file.isDirectory()) {
			this.modifiedFiles.add(file);
		}
		else{
			String mimeType = mimeTypeRegistry.getMimeType(file);
			if(this.indexerFactory.getMappedMimeTypes().contains(mimeType)){
				this.modifiedFiles.add(file);
				this.checkUpdateCondition();
			}
			else{
				log.debug("No indexer for file: " + file.getPath());
			}
		}
	}
	
	private void checkUpdateCondition(){
		this.fileCount = fileCount + 1;
		this.ensureEnoughHeapMemory();
		if(fileCount >= this.refreschLimit){
			this.refreschIndex();
			this.fileCount = 0;
		}
	}
	
	private void performIndexSearchUpdate(){
		try {
			log.info("Create new IndesSearcher");
			indexSearcher = new IndexSearcher(indexDirectory);
			log.info("IndesSearcher created");
			indexSearchBean.setIndexSearcher(indexSearcher);
			if(this.globalSearchCache != null){
				this.globalSearchCache.invalidate();
			}
			if(this.sessionSearchCache != null){
				this.sessionSearchCache.invalidate();
			}
			log.info("Caches invalidated");			
		} catch (CorruptIndexException e) {
			log.error("Index corrupted", e);
		} catch (IOException e) {
			log.error("IOException while opening index", e);
		}
	}
	
	private void ensureEnoughHeapMemory(){
		if(Runtime.getRuntime().freeMemory()/(1024*1024) < 2){
			log.debug("Start garbage collection");
			Runtime.getRuntime().gc();
			log.debug("Garbage collection completed");
			log.debug("------------------Memory statistics------------------");			
			log.debug("Total Memory "+ memoryFormater.format(Runtime.getRuntime().totalMemory()/(1024*1024)) + " MB");
		    log.debug("Free Memory "+ memoryFormater.format(Runtime.getRuntime().freeMemory()/(1024*1024)) + " MB");
		    log.debug("Max Memory "+ memoryFormater.format(Runtime.getRuntime().maxMemory()/(1024*1024)) + " MB");
		    log.debug("------------------Memory statistics------------------");
		}
	}

	public IndexerFactory getIndexerFactory() {
		return indexerFactory;
	}

	public void setIndexerFactory(IndexerFactory indexerFactory) {
		this.indexerFactory = indexerFactory;
	}

	public void setIndexSearchBean(IndexSearchBean indexSearchBean) {
		this.indexSearchBean = indexSearchBean;
	}

	public void setGlobalSearchCache(GlobalSearchCache globalSearchCache) {
		this.globalSearchCache = globalSearchCache;
	}

	public void setSessionSearchCache(SessionSearchCache sessionSearchCache) {
		this.sessionSearchCache = sessionSearchCache;
	}
	
	private void setStatusMessage(String msg) {
		this.statusMessage = msg;
		if (this.jobExecutionContext != null){
			this.jobExecutionContext.setResult(msg);
		}
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setJobExecutionContext(JobExecutionContext jobExecutionContext) {
		this.jobExecutionContext = jobExecutionContext;
	}

	public void setMimeTypeRegistry(MimeTypeRegistry mimeTypeRegistry) {
		this.mimeTypeRegistry = mimeTypeRegistry;
	}

}
