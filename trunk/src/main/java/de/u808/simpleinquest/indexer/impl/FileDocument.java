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

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import de.u808.common.MD5Util;
import de.u808.simpleinquest.indexer.Indexer;

/** A utility for making Lucene Documents from a File. */

public class FileDocument {
	/**
	 * Makes a document for a File.
	 * <p>
	 * The document has three fields:
	 * <ul>
	 * <li><code>path</code>--containing the pathname of the file, as a
	 * stored, untokenized field;
	 * <li><code>modified</code>--containing the last modified date of the
	 * file as a field as created by <a
	 * href="lucene.document.DateTools.html">DateTools</a>; and
	 * <li><code>contents</code>--containing the full contents of the file,
	 * as a Reader field;
	 */
	public static Document Document(File f)
			throws java.io.FileNotFoundException {

		// make a new, empty document
		Document doc = new Document();
		return FileDocument.Document(doc, f);
	}

	public static Document Document(Document document, File f)
			throws java.io.FileNotFoundException {
		// Add the path of the file as a field named "path". Use a field that is
		// indexed (i.e. searchable), but don't tokenize the field into words.
		if (document.getField(Indexer.PATH_FIELD_NAME) != null) {
			document.removeFields(Indexer.PATH_FIELD_NAME);
		}
		document.add(new Field(Indexer.PATH_FIELD_NAME, f.getPath(),
				Field.Store.YES, Field.Index.UN_TOKENIZED));

		// Add the last modified date of the file a field named "modified". Use
		// a field that is indexed (i.e. searchable), but don't tokenize the
		// field
		// into words.
		if(document.getField(Indexer.MODIFIED_FIELD_NAME) != null){
			document.removeFields(Indexer.MODIFIED_FIELD_NAME);
		}
		document.add(new Field(Indexer.MODIFIED_FIELD_NAME, DateTools
				.timeToString(f.lastModified(), DateTools.Resolution.MINUTE),
				Field.Store.YES, Field.Index.UN_TOKENIZED));

		document.add(new Field(Indexer.ID_FIELD_NAME, MD5Util.getInstance().getMD5Hex(f.getPath()), Field.Store.YES, Field.Index.UN_TOKENIZED));

		// return the document
		return document;
	}

	protected FileDocument() {
	}
}
