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

import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import de.u808.simpleinquest.config.DirectoryConfiguration;
import de.u808.simpleinquest.indexer.impl.IndexUpdater;
import de.u808.simpleinquest.util.DirectoryTraverser;
import de.u808.simpleinquest.util.InvalidArgumentException;
import de.u808.simpleinquest.web.ConfigBeanResource;

public class IndexUpdaterTest {
	
	private static IndexUpdater INDEX_UPDATER;
	
	private static ConfigBeanResource CONFIG_BEAN;
	
	@BeforeClass
	public static void init(){
		File contextFile = new File("WebContent/WEB-INF/applicationContext.xml");
		Resource res = new FileSystemResource(contextFile);
		XmlBeanFactory factory = new XmlBeanFactory(res);
        INDEX_UPDATER = (IndexUpdater) factory.getBean("indexUpdater");
        CONFIG_BEAN = (ConfigBeanResource) factory.getBean("configBeanResource");
	}
	
	@Test
	public void testUpdate(){
		Exception exception = null;
		List<DirectoryConfiguration> dirs = CONFIG_BEAN.getSystemConfig().getConfiguration().getDirectoriesToIndex();
		try {
			for(DirectoryConfiguration directoryConfiguration : dirs){
				String dir = directoryConfiguration.getPath();
				new DirectoryTraverser(new File(dir), INDEX_UPDATER, DirectoryTraverser.TraversalMode.JustDirectories).startTraversal();
			}
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
			exception = e;
		}
		assertNull(exception);
	}

}
