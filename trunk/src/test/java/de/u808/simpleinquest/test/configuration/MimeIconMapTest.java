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

package de.u808.simpleinquest.test.configuration;

import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

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
