package de.u808.simpleinquest.web.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import de.u808.simpleinquest.service.MimeTypeRegistry;
import de.u808.simpleinquest.service.search.IndexSearchBean;

public class DownloadController implements Controller{
	
	private IndexSearchBean indexSearchBean;
	
	private MimeTypeRegistry mimeTypeRegistry;
	
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
				String filePrefix = hit.get(Indexer.FILE_FETCH_PREFIX_FIELD_NAME);
				boolean blockDirectDownload = Boolean.valueOf(hit.get(Indexer.PREVENT_DIRECT_DOWNLOAD_FIELD_NAME));
				File file = new File(filePath);
				
				if(!blockDirectDownload && StringUtils.isEmpty(filePrefix)){
					handleDownload(response, file);
				}
				else{
					
				}
				
				
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

	public void setMimeTypeRegistry(MimeTypeRegistry mimeTypeRegistry) {
		this.mimeTypeRegistry = mimeTypeRegistry;
	}

	private void handleDownload(HttpServletResponse response, File file) throws IOException{
		response.reset();
		response.setContentType(mimeTypeRegistry.getMimeType(file));
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
}
