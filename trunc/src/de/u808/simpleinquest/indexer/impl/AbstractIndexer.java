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
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;


public abstract class AbstractIndexer implements Indexer{
	
	protected Document document;
	
	protected File file;
	
	private static Log log = LogFactory.getLog(AbstractIndexer.class);
	
	public Document indexFile(File file) throws FileNotFoundException{
		this.file = file;
		this.document = FileDocument.Document(file);
		try{
			this.setContentsFild(file);
		} catch (IndexerException ie){
			log.error(ie.getMessage());
		} catch (Exception e) {
			log.error("Exception while fetch content of file: " + file.toString(), e);
			if(this.document != null){
				List<Field> fields = this.document.getFields();
				if(fields != null){
					for(Field f : fields){
						document.removeField(f.name());
					}
				}
				this.document = null;
			}
		} finally {
			this.file = null;						
		}
		return document;
	}
	
	public abstract void setContentsFild(File file) throws Exception;

}
