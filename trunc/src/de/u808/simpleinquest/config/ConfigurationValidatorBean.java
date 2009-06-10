package de.u808.simpleinquest.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigurationValidatorBean {

    private Properties systemProperties;
    
    private IndexerConfigurationBean indexerConfigurationBean;

    public List<ConfigurationError> validate(){
        List<ConfigurationError> configurationErrors = new LinkedList<ConfigurationError>();
        //Is system home a dir and writable
        String configDirString = this.systemProperties.getProperty(ConfigurationConstants.SIMPLE_INQUEST_HOME_DIR);
        if(StringUtils.isEmpty(configDirString)){
                configurationErrors.add(new ConfigurationError("SimpleInquestHome is not set", ConfigurationError.Severity.FATAL));
        }
        else{
                File homeDir = new File(configDirString);
                if(!homeDir.exists()){
                        configurationErrors.add(new ConfigurationError("SimpleInquestHome: directory " + homeDir + " can't be found", ConfigurationError.Severity.FATAL));                         
                }
                else{
                        if(!homeDir.isDirectory()){
                                configurationErrors.add(new ConfigurationError("SimpleInquestHome: " + homeDir + " isn't a directory", ConfigurationError.Severity.FATAL));
                        }
                        else{
                                if(!homeDir.canRead() && !homeDir.canWrite()){
                                        configurationErrors.add(new ConfigurationError("SimpleInquestHome: insufficient access authorisation for directory " + homeDir, ConfigurationError.Severity.FATAL));
                                }
                        }
                }
        }
        if(this.indexerConfigurationBean.getIndexerConfiguration().getMimeTypeIndexerMap().isEmpty()){
                configurationErrors.add(new ConfigurationError("Missing indexer configuration", ConfigurationError.Severity.FATAL));
        }
        
        return configurationErrors;
    }

    public Properties getSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(Properties systemProperties) {
        this.systemProperties = systemProperties;
    }

    public IndexerConfigurationBean getIndexerConfigurationBean() {
        return indexerConfigurationBean;
    }

    public void setIndexerConfigurationBean(IndexerConfigurationBean indexerConfigurationBean) {
        this.indexerConfigurationBean = indexerConfigurationBean;
    }
    
}
