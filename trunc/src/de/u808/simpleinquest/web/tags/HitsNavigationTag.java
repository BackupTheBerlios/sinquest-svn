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
			boolean isFirstPage = search.getPageIndex() == 0;
			boolean isLastPage = search.getPageIndex() == search.getPageCount()-1;
			if(!isFirstPage){
				out.println("<a href=\"search.htm?searchString=" +  search.getSearchString()+ "&pageIndex=0\"> ");
			}
			out.println("<img src=\"/SimpleInquest/img/start.gif\" alt=\"Erste Seite\"/>");
			if(!isFirstPage){
				out.println("</a>");
				out.println("<a href=\"search.htm?searchString=" +  search.getSearchString()+ "&pageIndex=" + (search.getPageIndex()-1) + "\"> ");
			}
			out.println("<img src=\"/SimpleInquest/img/left.gif\" alt=\"Vorherige Seite\"/>");
			if(!isFirstPage){
				out.println("</a>");
			}
			for(int i=0; i< search.getPageCount(); i++){
				if(i != search.getPageIndex()){
					out.println("<a href=\"search.htm?searchString=" +  search.getSearchString()+ "&pageIndex=" + i + "\"> " + (i+1) + "</a>");
				}
				else{
					out.print(i+1);
				}
			}
			if(!isLastPage){
				out.println("<a href=\"search.htm?searchString=" +  search.getSearchString()+ "&pageIndex=" + (search.getPageIndex()+1) + "\"> ");
			}
			out.println("<img src=\"/SimpleInquest/img/right.gif\" alt=\"Folgende Seite\"/>");
			if(!isLastPage){
				out.println("</a>");
				out.println("<a href=\"search.htm?searchString=" +  search.getSearchString()+ "&pageIndex=" + (search.getPageCount()-1) + "\"> ");
			}
			out.println("<img src=\"/SimpleInquest/img/end.gif\" alt=\"Letzte Seite\"/>");
			if(!isLastPage){
				out.println("</a>");
			}
		} catch (Exception e) {
			log.error("Tag error", e);
		}

	}

}
