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
