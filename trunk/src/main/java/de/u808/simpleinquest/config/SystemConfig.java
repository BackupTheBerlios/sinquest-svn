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

package de.u808.simpleinquest.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import de.u808.simpleinquest.config.ConfigurationError.Severity;

public class SystemConfig {

	private static SystemConfig SYSTEM_CONFIG_INSTANCE = null;
	
	public static String INDEX_DIRECTORY = "index";
	
	private File indexDirectory = null;
	
	private Configuration configuration;
	
	private List<ConfigurationError> configurationErrors = null;
	
	Log log = LogFactory.getLog(this.getClass());
	
	private ApplicationContext applicationContext;
	
	public SystemConfig (Configuration configuration) {
		this.configuration = configuration;
		SYSTEM_CONFIG_INSTANCE = this;
		this.checkConfiguration();
	}
	
	public static SystemConfig getInstance(){		
		return SYSTEM_CONFIG_INSTANCE;
	}
	
	private void checkConfiguration(){
		this.configurationErrors = new LinkedList<ConfigurationError>();
		//Is system home a dir and writable
		String configDirString = configuration.getSimpleInquestHome();
		if(StringUtils.isEmpty(configDirString)){
			this.configurationErrors.add(new ConfigurationError("SimpleInquestHome is not set", ConfigurationError.Severity.FATAL));
		}
		else{
			File homeDir = new File(configDirString);
			if(!homeDir.exists()){
				this.configurationErrors.add(new ConfigurationError("SimpleInquestHome: directory " + homeDir + " can´t be found", ConfigurationError.Severity.FATAL));				
			}
			else{
				if(!homeDir.isDirectory()){
					this.configurationErrors.add(new ConfigurationError("SimpleInquestHome: " + homeDir + " isn´t a directory", ConfigurationError.Severity.FATAL));
				}
				else{
					if(!homeDir.canRead() && !homeDir.canWrite()){
						this.configurationErrors.add(new ConfigurationError("SimpleInquestHome: insufficient access authorisation for directory " + homeDir, ConfigurationError.Severity.FATAL));
					}
				}
			}
		}
		if(configuration.getIndexerConfiguration().getMimeTypeIndexerMap().isEmpty()){
			this.configurationErrors.add(new ConfigurationError("Missing indexer configuration", ConfigurationError.Severity.FATAL));
		}
	}

	public boolean isSystemConfiguredCorectly() {
		return configurationErrors == null ? true : this.configurationErrors.isEmpty();
	}
	
	public List<ConfigurationError> getConfigurationErrors(){
		return this.configurationErrors;
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	
	public File getIndexDirectory(){
		if(indexDirectory == null && StringUtils.isNotEmpty(configuration.getSimpleInquestHome())){
			this.indexDirectory = new File(configuration.getSimpleInquestHome(), INDEX_DIRECTORY);
		}
		return indexDirectory;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public boolean hasFatalErrors(){
		for(ConfigurationError error : configurationErrors){
			if (error.getSeverity().compareTo(Severity.FATAL) == 0){
				return true;
			}
		}
		return false;
	}
	
	public void addError(ConfigurationError error){
		if(this.configurationErrors == null){
			configurationErrors = new LinkedList<ConfigurationError>();
		}
		configurationErrors.add(error);
	}
}
