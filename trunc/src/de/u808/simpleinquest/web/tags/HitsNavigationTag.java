package de.u808.simpleinquest.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import de.u808.simpleinquest.domain.Search;

public class HitsNavigationTag extends SimpleTagSupport {
	
	private Search search;	

	public void setSearch(Search search) {
		this.search = search;
	}

	public void doTag() throws JspException {

		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			
			out.println(search.getHitsCount() + " Ergebnisse");
		} catch (Exception e) {
			// Ignore.
		}

	}

}
