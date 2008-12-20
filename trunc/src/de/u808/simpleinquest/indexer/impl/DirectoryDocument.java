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

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;

public class DirectoryDocument extends FileDocument {

	private static final String SEMICOLON = ";";

	private DirectoryDocument() {
		super();
	}

	public static Document Document(File f)
			throws java.io.FileNotFoundException {
		Document doc = FileDocument.Document(f);
		StringBuilder stringBuilder = new StringBuilder();
		if(f != null && f.canRead()){
			String[] fileList = f.list();
			if(fileList != null){
				for(String fileName : fileList){
					stringBuilder.append(fileName);
					stringBuilder.append(SEMICOLON);
				}
			}
		}
		doc.add(new Field(Indexer.DIRECTORY_LIST_FIELD_NAME, stringBuilder.toString(), Field.Store.YES, Field.Index.UN_TOKENIZED));
		return doc;
	}
}
