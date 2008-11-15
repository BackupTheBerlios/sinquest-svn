package de.u808.simpleinquest.web.tags;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

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
