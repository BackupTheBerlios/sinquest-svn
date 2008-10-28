package de.u808.simpleinquest.test.indexer.impl;

import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
		String[] dirs = CONFIG_BEAN.getSystemConfig().getConfiguration().getDirectoriesToIndexList();
		try {
			for(String dir : dirs){
				new DirectoryTraverser(new File(dir), INDEX_UPDATER, DirectoryTraverser.TraversalMode.JustDirectories).startTraversal();
			}
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
			exception = e;
		}
		assertNull(exception);
	}

}
