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

package de.u808.simpleinquest.repository;

import java.util.List;

import de.u808.simpleinquest.domain.DocumentDirectory;

public interface DocumentDirectoryDAO extends GenericDAO<DocumentDirectory, Long>{
	
	public DocumentDirectory findByPath(String path);
	
	public List<DocumentDirectory> findByExample(DocumentDirectory documentDirectory);

}
