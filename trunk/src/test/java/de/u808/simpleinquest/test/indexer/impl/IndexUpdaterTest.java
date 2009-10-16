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

package de.u808.simpleinquest.test.indexer.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.u808.simpleinquest.indexer.impl.IndexUpdater;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"})
public class IndexUpdaterTest {
	
	@Autowired
	@Qualifier("indexUpdater")
	IndexUpdater indexUpdater;
	
	@Test
	public void testSetup(){
		assertNotNull(indexUpdater);
	}
	
	@Test
	@Ignore
	public void testUpdate(){
	    //TODO fix it
//		Exception exception = null;
//		List<DirectoryConfiguration> dirs = CONFIG_BEAN.getSystemConfig().getConfiguration().getDirectoriesToIndex();
//		try {
//			for(DirectoryConfiguration directoryConfiguration : dirs){
//				String dir = directoryConfiguration.getPath();
//				new DirectoryTraverser(new File(dir), INDEX_UPDATER, DirectoryTraverser.TraversalMode.JustDirectories).startTraversal();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			exception = e;
//		}
//		assertNull(exception);
	}

}
