package de.u808.simpleinquest.config;

import java.util.Properties;

import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.annotation.ExternalValue;
import org.springframework.config.java.annotation.valuesource.PropertiesValueSource;
import org.springframework.config.java.support.ConfigurationSupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertiesValueSource(locations={"classpath:de/u808/simpleinquest/config/home.properties"})
public class SimpleInquestConfig extends ConfigurationSupport{
    
    @ExternalValue(ConfigurationConstants.SIMPLE_INQUEST_HOME_DIR) String homeDirectory;

    @Bean(initMethodName = "initMimeIconMap")
    public MimeIconMapBean mimeIconMapBean(){
        MimeIconMapBean mimeIconMapBean = new MimeIconMapBean();
        Resource resource = new ClassPathResource("MimeIconMapConf.xml");
        mimeIconMapBean.setConfigResource(resource);
        return mimeIconMapBean;
    }
    
    @Bean(initMethodName = "initIndexerMap")
    public IndexerConfigurationBean indexerConfigurationBean(){
        IndexerConfigurationBean indexerConfigurationBean = new IndexerConfigurationBean();
        Resource resource = new ClassPathResource("IndexerConf.xml");
        indexerConfigurationBean.setConfigResource(resource);
        return indexerConfigurationBean;
    }
    
    @Bean
    public ConfigurationValidatorBean configurationValidatorBean(){
        ConfigurationValidatorBean configurationValidatorBean = new ConfigurationValidatorBean();
        configurationValidatorBean.setSystemProperties(systemProperties());
        configurationValidatorBean.setIndexerConfigurationBean(indexerConfigurationBean());
        return configurationValidatorBean;
    }
    
    @Bean
    public Properties systemProperties(){
        Properties systemProperties = new Properties();
        systemProperties.put(ConfigurationConstants.SIMPLE_INQUEST_HOME_DIR, homeDirectory);
        return systemProperties;
    }

}
