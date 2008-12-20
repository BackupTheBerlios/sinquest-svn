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
import java.io.FileReader;

import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;

public class PlainTextIndexer extends AbstractIndexer {

	@Override
	public void setContentsFild(File file) throws Exception {
		FileReader fileReader = new FileReader(file);
		this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, fileReader));
	}

}
