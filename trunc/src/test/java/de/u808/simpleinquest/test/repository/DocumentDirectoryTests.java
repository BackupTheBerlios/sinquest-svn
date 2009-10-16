/*
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

package de.u808.simpleinquest.test.repository;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.u808.simpleinquest.domain.Document;
import de.u808.simpleinquest.domain.DocumentConfig;
import de.u808.simpleinquest.domain.DocumentDirectory;
import de.u808.simpleinquest.repository.DocumentDirectoryDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../test-applicationContext.xml"})
public class DocumentDirectoryTests {
	
	@Autowired
	private ApplicationContext applicationContext;
    
	@Autowired
    private DocumentDirectoryDAO documentDirectoryDAO;
	
    @Test
    public void testInsert(){
    	DocumentConfig config = new DocumentConfig();
    	config.setBlockDirectDownload(false);
    	config.setFetchPrefix("c:/testtest");
    	DocumentDirectory documentDirectory = new DocumentDirectory();
    	documentDirectory.setHash("123456");
    	documentDirectory.setDocumentConfig(config);
    	Set<Document> testSet = new HashSet<Document>();
    	Document document = new Document();
    	document.setParent(documentDirectory);
    	document.setHash("12345");
    	testSet.add(document);
    	documentDirectory.setDocuments(testSet);
    	documentDirectory.setId(7l);
    	DocumentDirectory testDirectory = documentDirectoryDAO.makePersistent(documentDirectory);
    	assertNotNull(testDirectory);
    	assertNotNull(testDirectory.getId());
    }

}
