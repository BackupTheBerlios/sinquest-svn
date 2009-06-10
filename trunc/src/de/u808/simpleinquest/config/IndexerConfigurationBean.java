package de.u808.simpleinquest.config;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import de.u808.simpleinquest.util.SimpleXMLUtil;

public class IndexerConfigurationBean {

    private Resource configResource;
    
    private IndexerConfiguration indexerConfiguration;
    
    private final static Log log = LogFactory.getLog(IndexerConfigurationBean.class);

    public Resource getConfigResource() {
        return configResource;
    }

    public void setConfigResource(Resource configResource) {
        this.configResource = configResource;
    }
    
    public IndexerConfiguration getIndexerConfiguration() {
        return indexerConfiguration;
    }

    public void setIndexerConfiguration(IndexerConfiguration indexerConfiguration) {
        this.indexerConfiguration = indexerConfiguration;
    }

    public void initIndexerMap(){
        try {
            if(configResource != null && configResource.exists()){
                this.indexerConfiguration = (IndexerConfiguration) SimpleXMLUtil.getObject(IndexerConfiguration.class, configResource.getFile());
            }
            else{
                log.error("Config can not be found");
            }
        } catch (IOException e) {
            log.error("Config can not be found", e);
        }
    }
    
}
