package de.u808.simpleinquest.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemConfig {

	private static SystemConfig SYSTEM_CONFIG_INSTANCE = null;
	
	public static String INDEX_DIRECTORY = "index";
	
	private File indexDirectory = null;
	
	private Configuration configuration;
	
	private List<String> configurationErrors = null;
	
	Log log = LogFactory.getLog(this.getClass());
	
	public SystemConfig (Configuration configuration) {
		this.configuration = configuration;
		SYSTEM_CONFIG_INSTANCE = this;
		this.checkConfiguration();
	}
	
	public static SystemConfig getInstance(){		
		return SYSTEM_CONFIG_INSTANCE;
	}
	
	private void checkConfiguration(){
		this.configurationErrors = new LinkedList<String>();
		//Is system home a dir and writable
		String configDirString = configuration.getSimpleInquestHome();
		if(StringUtils.isEmpty(configDirString)){
			this.addErrorEntry("SimpleInquestHome is not set");
		}
		else{
			File homeDir = new File(configDirString);
			if(!homeDir.exists()){
				this.addErrorEntry("SimpleInquestHome: directory " + homeDir + " can´t be found");
			}
			else{
				if(!homeDir.isDirectory()){
					this.addErrorEntry("SimpleInquestHome: " + homeDir + " isn´t a directory");
				}
				else{
					if(!homeDir.canRead() && !homeDir.canWrite()){
						this.addErrorEntry("SimpleInquestHome: insufficient access authorisation for directory " + homeDir);
					}
				}
			}
		}
		if(configuration.getIndexerConfiguration().getMimeTypeIndexerMap().isEmpty()){
			this.addErrorEntry("Missing indexer configuration");
		}
	}
	
	public void addErrorEntry(String error){
		this.addErrorEntry(error, null);
	}
	
	public void addErrorEntry(String error, Throwable e) {
		if(e != null){
			log.error(error);
		}
		else{
			log.error(error, e);
		}
		configurationErrors.add(error);
	}

	public boolean isSystemConfiguredCorectly() {
		return configurationErrors == null ? true : this.configurationErrors.isEmpty();
	}
	
	public List<String> getConfigurationErrors(){
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
	
}
