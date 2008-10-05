package de.u808.simpleinquest.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;
import org.springframework.core.io.Resource;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.config.SystemConfig;

public class ConfigBeanResource {

    private Resource configResource;
    
    private SystemConfig systemConfig;
    
    private static Log log = LogFactory.getLog(ConfigBeanResource.class);
	
	public ConfigBeanResource(Resource resource) {
		this.configResource = resource;
	}
	
	public File getConfigFile() throws IOException{
		return this.configResource.getFile();
	}
	
	public SystemConfig getSystemConfig (){
		if(systemConfig == null){
			Serializer serializer = new Persister();
			try {
				Configuration conf = serializer.read(Configuration.class, configResource.getFile());
				this.systemConfig = new SystemConfig(conf);
			} catch (Exception e) {
				log.error("Can´t load system config", e);
				return null;
			}
		}
		return systemConfig;
	}

}
