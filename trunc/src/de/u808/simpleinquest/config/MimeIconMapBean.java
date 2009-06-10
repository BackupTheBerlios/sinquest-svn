package de.u808.simpleinquest.config;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import de.u808.simpleinquest.util.SimpleXMLUtil;

public class MimeIconMapBean {

    private Resource configResource;
    
    private MimeIconMap mimeIconMap;
    
    private final static Log log = LogFactory.getLog(MimeIconMapBean.class);

    public Resource getConfigResource() {
        return configResource;
    }

    public void setConfigResource(Resource configResource) {
        this.configResource = configResource;
    }

    public MimeIconMap getMimeIconMap() {
        return mimeIconMap;
    }

    public void setMimeIconMap(MimeIconMap mimeIconMap) {
        this.mimeIconMap = mimeIconMap;
    }
    
    public void initMimeIconMap(){
        try {
            if(configResource != null && configResource.exists()){
                this.mimeIconMap = (MimeIconMap) SimpleXMLUtil.getObject(MimeIconMap.class, configResource.getFile());
            }
            else{
                log.error("Config can not be found");
            }
        } catch (IOException e) {
            log.error("Config can not be found", e);
        }
    }
}
