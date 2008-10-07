package de.u808.simpleinquest.service.search;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.beans.factory.InitializingBean;

import de.u808.simpleinquest.web.ConfigBeanResource;

public class IndexSearchBean implements InitializingBean {
	
	private IndexSearcher indexSearcher;
	
	private ConfigBeanResource configBeanResource;
	
	private static Log log = LogFactory.getLog(IndexSearchBean.class);

	public IndexSearcher getIndexSearcher(){
		return this.indexSearcher;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		this.indexSearcher = indexSearcher;
	}

	public ConfigBeanResource getConfigBeanResource() {
		return configBeanResource;
	}

	public void setConfigBeanResource(ConfigBeanResource configBeanResource) {
		this.configBeanResource = configBeanResource;
	}

	public void afterPropertiesSet() throws Exception {
		try {
			this.indexSearcher = new IndexSearcher(configBeanResource.getSystemConfig().getIndexDirectory().getPath());
		} catch (CorruptIndexException e) {
			log.error("Index is corrupt! No search possible!", e);
		} catch (IOException e) {
			log.error("Can´t access index files! No search possible!", e);
		}
	}

}
