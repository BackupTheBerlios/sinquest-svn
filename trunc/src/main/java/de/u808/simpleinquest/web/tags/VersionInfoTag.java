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

package de.u808.simpleinquest.web.tags;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

public class VersionInfoTag extends SimpleTagSupport{
	
	private static Log log = LogFactory.getLog(VersionInfoTag.class);
	
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {			
			ClassPathResource classPathResource = new ClassPathResource("version.properties");
			if(classPathResource.exists()){
				Properties properties = new Properties();
				properties.load(classPathResource.getInputStream());
				out.println("<span id=\"version\"> " + properties.getProperty("app.version") + " (Revision " + properties.getProperty("svn.revision") + " Build " + properties.getProperty("build.number") + ")</span>");
			}
			else {
				log.warn("Version properties not found");
			}
		} catch (Exception e) {
			log.error("Tag error", e);
		}
	}

}
