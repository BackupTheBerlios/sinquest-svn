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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VersionInfoTag extends SimpleTagSupport {

	private static Log log = LogFactory.getLog(VersionInfoTag.class);

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			String appServerHome = pageContext.getServletContext().getRealPath("/");

			File manifestFile = new File(appServerHome, "META-INF/MANIFEST.MF");

			Manifest mf = new Manifest();
			mf.read(new FileInputStream(manifestFile));

			Attributes atts = mf.getMainAttributes();

			out.println("<span id=\"version\"> (Revision " + atts.getValue("Implementation-Version") + " Build " + atts.getValue("Implementation-Build") + " Built-By " + atts.getValue("Built-By") +")</span>");
		} catch (Exception e) {
			log.error("Tag error", e);
		}
	}

}
