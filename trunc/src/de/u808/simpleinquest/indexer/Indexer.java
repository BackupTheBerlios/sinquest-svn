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

package de.u808.simpleinquest.indexer;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.lucene.document.Document;
import org.apache.tika.metadata.Metadata;

import de.u808.simpleinquest.indexer.impl.IndexerException;

public interface Indexer {
	
	public static String CONTENT_FIELD_NAME = "contents";
	public static String PATH_FIELD_NAME = "path";
	public static String ID_FIELD_NAME = "doc-ID";
	public static String MODIFIED_FIELD_NAME = Metadata.LAST_MODIFIED;
	public static String DIRECTORY_LIST_FIELD_NAME = "directoryList";
	
	public static String AUTOR_FIELD_NAME = Metadata.AUTHOR;
	public static String TITLE_FIELD_NAME = Metadata.AUTHOR;

	public Document indexFile(File file) throws FileNotFoundException, IndexerException;
}
