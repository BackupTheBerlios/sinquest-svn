package de.u808.simpleinquest.web.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.TermQuery;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.service.search.IndexSearchBean;

public class DownloadController implements Controller{
	
	private IndexSearchBean indexSearchBean;
	
	private byte[] buf = new byte[18000];


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileId = request.getParameter("id");
		
		if(StringUtils.isNotEmpty(fileId)){
			//Search document
			TermQuery query = new TermQuery(new Term(Indexer.ID_FIELD_NAME, fileId));
			Hits hits = indexSearchBean.getIndexSearcher().search(query);
			if(hits.length() > 0){
				Hit hit = (Hit) hits.iterator().next();
				String filePath = hit.get(Indexer.PATH_FIELD_NAME);
				File file = new File(filePath);
				
				response.reset();
    			//response.setContentType(MimeUtil.getMimeType(file));
				response.setContentType(request.getSession().getServletContext().getMimeType(file.getName()));
    			response.setHeader("Pragma", "private");
    			response.setHeader("Cache-Control", "private, must-revalidate");
    			response.setHeader("Content-Disposition", "attachment; filename=\""
    			+ file.getName() + "\"");
				
				FileInputStream fis = new FileInputStream(file);
                OutputStream os = response.getOutputStream();
                int len = -1;
                int x = 0;
                while((len = fis.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
    			
    			response.getOutputStream().flush();
    			response.getOutputStream().close();
			}			
			return null;
		}
		//TODO create View
		return new ModelAndView("fileNotFound");
	}

	public IndexSearchBean getIndexSearchBean() {
		return indexSearchBean;
	}

	public void setIndexSearchBean(IndexSearchBean indexSearchBean) {
		this.indexSearchBean = indexSearchBean;
	}

}
