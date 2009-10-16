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

package de.u808.simpleinquest.domain;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;

import de.u808.simpleinquest.indexer.Indexer;

public class SimpleDocument {
	
	private Document document;
	
	private float score;
	
	public SimpleDocument(Document document, float score){
		this.document = document;
		this.score = score;
	}
	
	public String getPath(){
		if(document != null){
			return document.get(Indexer.PATH_FIELD_NAME);
		}
		else {
			return null;
		}
	}
	
	public String getId(){
		if(document != null){
			return document.get(Indexer.ID_FIELD_NAME);
		}
		else {
			return null;
		}
	}
	
	public String getFileName(){
		if(document != null){
			String path = document.get(Indexer.PATH_FIELD_NAME);
			int pos = path.lastIndexOf(File.separator);
			if(pos != -1 && pos < path.length()-1){
				return path.substring(pos+1);
			}
		}
		return null;
	}
	
	public Date getLastModified(){
		if(document != null){
			try {
				return DateTools.stringToDate(document.get(Indexer.MODIFIED_FIELD_NAME));
			} catch (ParseException e) {
				return null;
			}
		}
		else {
			return null;
		}
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public boolean getBlockDownload(){
		//TODO block download ...
//		if(document != null){
//			return Boolean.valueOf((document.get(Indexer.PREVENT_DIRECT_DOWNLOAD_FIELD_NAME)));
//		}
//		else {
//			return true;
//		}
		return false;
	}
}
