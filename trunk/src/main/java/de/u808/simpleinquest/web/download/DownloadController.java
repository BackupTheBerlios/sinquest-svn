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

package de.u808.simpleinquest.web.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.TermQuery;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;
import org.springframework.web.servlet.mvc.Controller;

import de.u808.simpleinquest.domain.SearchResult;
import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.service.MimeTypeRegistry;
import de.u808.simpleinquest.service.search.IndexSearchBean;
import de.u808.simpleinquest.service.search.SearchManager;

public class DownloadController extends AbstractCommandController{

	private IndexSearchBean indexSearchBean;

	private SearchManager searchManager;

	private MimeTypeRegistry mimeTypeRegistry;

	private byte[] buf = new byte[18000];
	
	public DownloadController(){
		setCommandClass(DownloadCommand.class);
		setCommandName("download");
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

	private void handleDownload(HttpServletResponse response, File file)
			throws IOException {
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
		while ((len = fis.read(buf)) != -1) {
			os.write(buf, 0, len);
		}

		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object commandObject, BindException bindException)
			throws Exception {
		
		DownloadCommand downloadCommand = (DownloadCommand) commandObject;
		TermQuery query = new TermQuery(new Term(Indexer.ID_FIELD_NAME,
				downloadCommand.getFileId()));
		Hits hits = indexSearchBean.getIndexSearcher().search(query);
		if (hits.length() > 0) {
			Hit hit = (Hit) hits.iterator().next();
			String filePath = hit.get(Indexer.PATH_FIELD_NAME);
			// String filePrefix =
			// hit.get(Indexer.FILE_FETCH_PREFIX_FIELD_NAME);
			// boolean blockDirectDownload =
			// Boolean.valueOf(hit.get(Indexer.PREVENT_DIRECT_DOWNLOAD_FIELD_NAME));

			// if(!blockDirectDownload && StringUtils.isEmpty(filePrefix)){
			File downloadFile = new File(filePath);
			if (downloadFile != null && downloadFile.canRead()) {
				handleDownload(response, downloadFile);
			} else {
				return this.handleError(response, downloadCommand, "download.file", "error.download.cannotAccessFile");
			}
			/*
			 * } else{
			 * 
			 * }
			 */

		}
		return this.handleError(response, downloadCommand, "download.file", "error.download.noFileWithIdIsStoredInTheIndex");
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}
	
	private ModelAndView handleError(HttpServletResponse response, DownloadCommand downloadCommand, String errorKey, String errorMessage) throws ParseException, IOException{
		SearchResult searchResult = this.searchManager.search(downloadCommand.getSearchString());
		searchResult.setPageIndex(downloadCommand.getPageIndex());
		searchResult.addErrorMessage(errorKey, errorMessage);
		response.sendRedirect("../search.htm?searchString=" + downloadCommand.getSearchString() + "&pageIndex=" + downloadCommand.getPageIndex());
		return null;
	}
}
