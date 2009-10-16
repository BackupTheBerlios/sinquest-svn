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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Hits;

public class PageModel {
	
	private int index;
	
	private int noDocuments;
	
	private List<SimpleDocument> results;
	
	private static Log log = LogFactory.getLog(PageModel.class);

	public int first;

	public int last;
	
	public PageModel (Hits hits, int index, int results){
		this.index = index;
		this.noDocuments = results;
		this.results = initResults(hits);
	}
	
	private List<SimpleDocument> initResults(Hits hits){
		LinkedList<SimpleDocument> currentResults = new LinkedList<SimpleDocument>();
		first = this.index;
		last = this.index + this.noDocuments -1;
		if(hits != null){
			if (hits.length() < last){
				last = hits.length()-1;
			}
		}
		for(int i=first; i<=last; i++){
			try {
				currentResults.add(new SimpleDocument(hits.doc(i), hits.score(i)));
			} catch (CorruptIndexException e) {
				log.error("Error fetching result", e);
			} catch (IOException e) {
				log.error("Error fetching result", e);
			}
		}
		return currentResults;
	}

	public List<SimpleDocument> getResults() {
		return results;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}
	
}
