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

package de.u808.simpleinquest.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.config.SystemConfig;

public class ConfigBeanResource implements InitializingBean, ApplicationContextAware{

    private Resource configResource;
    
    private SystemConfig systemConfig;

	private ApplicationContext applicationContext;
    
    private final static Log log = LogFactory.getLog(ConfigBeanResource.class);
	
	public ConfigBeanResource() {
		
	}
	
	public void setConfigResource(Resource configResource) {
		this.configResource = configResource;
	}

	private void readSystemConfig(){
		Serializer serializer = new Persister();
		try {
			File configFile;
			if(!configResource.exists()){
				log.error("Can not find config file!");
			}
			else{
				configFile = configResource.getFile();
				Configuration conf = serializer.read(Configuration.class, configFile);
				this.systemConfig = new SystemConfig(conf);
				this.systemConfig.setApplicationContext(this.applicationContext);
			}			
		} catch (Exception e) {
			log.error("Can�t load system config", e);
		}
	}
	
	public File getConfigFile() throws IOException{
		return this.configResource.getFile();
	}
	
	public SystemConfig getSystemConfig (){
		return systemConfig;
	}
	
//	private File compensateDifferentContext() throws IOException{
//		File parent = new File("./src/main/webapp/WEB-INF");
//		File file = new File(parent, this.configResource.getFilename());
//		String pwd = file.getAbsolutePath();
//		return file;
//	}

	public void afterPropertiesSet() throws Exception {
		this.readSystemConfig();
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
