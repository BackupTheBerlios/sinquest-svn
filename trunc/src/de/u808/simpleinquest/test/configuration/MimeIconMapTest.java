package de.u808.simpleinquest.test.configuration;

import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;

import de.u808.simpleinquest.config.MimeIconMap;

public class MimeIconMapTest {

	@Test
	public void testCreation(){
		Exception testE = null;
		MimeIconMap mimeIconMap = new MimeIconMap();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("htm", "img/test.gif");
		map.put("pdf", "img/test.gif");
		map.put("doc", "img/test.gif");
		mimeIconMap.setMap(map);
		
		Serializer serializer = new Persister();
		File result = new File("MimeIconMap.xml");

		try {
			serializer.write(mimeIconMap, result);
		} catch (Exception e) {
			testE = e;
			e.printStackTrace();
		}
		assertNull(testE);
	}

}
