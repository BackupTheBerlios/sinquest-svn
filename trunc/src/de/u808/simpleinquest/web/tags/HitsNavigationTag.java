package de.u808.simpleinquest.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.Search;

public class HitsNavigationTag extends SimpleTagSupport {
	
	private Search search;
	
	private static Log log = LogFactory.getLog(HitsNavigationTag.class);

	public void setSearch(Search search) {
		this.search = search;
	}

	public void doTag() throws JspException {

		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			for(int i=0; i< search.getPageCount(); i++){
				out.println("<a href=\"search.htm?searchString=" +  search.getSearchString()+ "&pageIndex=" + i + "\"> " + i + "</a>");
			}
		} catch (Exception e) {
			log.error("Tag error", e);
		}

	}

}
