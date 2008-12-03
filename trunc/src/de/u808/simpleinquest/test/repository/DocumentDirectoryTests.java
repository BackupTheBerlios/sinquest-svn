package de.u808.simpleinquest.test.repository;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.u808.simpleinquest.domain.Document;
import de.u808.simpleinquest.domain.DocumentDirectory;
import de.u808.simpleinquest.repository.DocumentDirectoryDAO;

public class DocumentDirectoryTests {
	
    private ApplicationContext ctx = null;
    
    private DocumentDirectoryDAO documentDirectoryDAO;
    
    @Before
	public void prepare(){
		String[] paths = {"./WebContent/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        
        documentDirectoryDAO = (DocumentDirectoryDAO) ctx.getBean("documentDirectoryDAO");
	}
    
    @Test
    public void testInsert(){
    	DocumentDirectory documentDirectory = new DocumentDirectory();
    	documentDirectory.setBlockDirectDownload(false);
    	documentDirectory.setDefaultFetchPrefixUrl("TestBlaBla");
    	documentDirectory.setPath("c:\\SimpleInquest\\docs");
    	Set<Document> testSet = new HashSet<Document>();
    	Document document = new Document();
    	document.setDocumentDirectory(documentDirectory);
    	document.setHash("12345");
    	testSet.add(document);
    	documentDirectory.setDocuments(testSet);
    	documentDirectoryDAO.makePersistent(documentDirectory);
    }

}
