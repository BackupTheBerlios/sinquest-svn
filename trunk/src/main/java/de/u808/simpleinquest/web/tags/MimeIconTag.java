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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.u808.simpleinquest.service.MimeIconRegistry;

public class MimeIconTag extends SimpleTagSupport {
	
	private static Log log = LogFactory.getLog(MimeIconTag.class);
	
	private String filename;
	
	private MimeIconRegistry mimeIconRegistry;

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	private void initMimeIconRegistry(){
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(((PageContext)getJspContext()).getServletContext());
		this.mimeIconRegistry = (MimeIconRegistry) context.getBean("mimeIconRegistry");
	}

	@Override
	public void doTag() throws JspException, IOException {
		if(this.mimeIconRegistry == null){
			this.initMimeIconRegistry();
		}
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			if(StringUtils.isNotEmpty(filename)){
				String iconURI = mimeIconRegistry.getMimeIcon(filename);			
				out.println("<img src=\"" + iconURI + "\" alt=\"Letzte Seite\"/>");
			}
		} catch (Exception e) {
			log.error("Tag error", e);
		}
	}

	
}
