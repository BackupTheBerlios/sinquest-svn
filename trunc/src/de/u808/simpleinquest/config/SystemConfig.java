package de.u808.simpleinquest.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import de.u808.simpleinquest.config.ConfigError.Severity;

public class SystemConfig {

	private static SystemConfig SYSTEM_CONFIG_INSTANCE = null;
	
	public static String INDEX_DIRECTORY = "index";
	
	private File indexDirectory = null;
	
	private Configuration configuration;
	
	private List<ConfigError> configurationErrors = null;
	
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
		this.configurationErrors = new LinkedList<ConfigError>();
		//Is system home a dir and writable
		String configDirString = configuration.getSimpleInquestHome();
		if(StringUtils.isEmpty(configDirString)){
			this.configurationErrors.add(new ConfigError("SimpleInquestHome is not set", ConfigError.Severity.FATAL));
		}
		else{
			File homeDir = new File(configDirString);
			if(!homeDir.exists()){
				this.configurationErrors.add(new ConfigError("SimpleInquestHome: directory " + homeDir + " can´t be found", ConfigError.Severity.FATAL));				
			}
			else{
				if(!homeDir.isDirectory()){
					this.configurationErrors.add(new ConfigError("SimpleInquestHome: " + homeDir + " isn´t a directory", ConfigError.Severity.FATAL));
				}
				else{
					if(!homeDir.canRead() && !homeDir.canWrite()){
						this.configurationErrors.add(new ConfigError("SimpleInquestHome: insufficient access authorisation for directory " + homeDir, ConfigError.Severity.FATAL));
					}
				}
			}
		}
		if(configuration.getIndexerConfiguration().getMimeTypeIndexerMap().isEmpty()){
			this.configurationErrors.add(new ConfigError("Missing indexer configuration", ConfigError.Severity.FATAL));
		}
	}

	public boolean isSystemConfiguredCorectly() {
		return configurationErrors == null ? true : this.configurationErrors.isEmpty();
	}
	
	public List<ConfigError> getConfigurationErrors(){
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
		for(ConfigError error : configurationErrors){
			if (error.getSeverity().compareTo(Severity.FATAL) == 0){
				return true;
			}
		}
		return false;
	}
	
	public void addError(ConfigError error){
		if(this.configurationErrors == null){
			configurationErrors = new LinkedList<ConfigError>();
		}
		configurationErrors.add(error);
	}
}
