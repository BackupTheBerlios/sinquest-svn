package de.u808.simpleinquest.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.Server;
import org.hsqldb.ServerConfiguration;
import org.hsqldb.persist.HsqlProperties;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springmodules.db.hsqldb.ServerBean;

import de.u808.simpleinquest.config.Configuration;
import de.u808.simpleinquest.web.ConfigBeanResource;

public class CustomServerBean extends ServerBean {
	
	private static String HSQL_SERVER_DATABASE_KEY = "server.database.0";
	
    private ConfigBeanResource configBeanResource;
    
    private Server server;
    
    /**
     * Commons Logging instance.
     */
    private static final Log log = LogFactory.getLog(ServerBean.class);
    
    public void afterPropertiesSet() throws Exception {
    	//change DB Path to be relative to SimpleInquestHome
    	String systemHomet = this.getConfiguration().getSimpleInquestHome();
    	String dbLocation = this.getServerProperties().getProperty(HSQL_SERVER_DATABASE_KEY);
    	//change property
    	this.getServerProperties().setProperty(HSQL_SERVER_DATABASE_KEY, new File(systemHomet, dbLocation).getPath());
        HsqlProperties configProps = new HsqlProperties(this.getServerProperties());
        if (configProps == null) {
            configProps = new HsqlProperties();
        }

        ServerConfiguration.translateDefaultDatabaseProperty(configProps);

        // finished setting up properties - set some important behaviors as well;
        server = new org.hsqldb.Server();
        server.setRestartOnShutdown(false);
        server.setNoSystemExit(true);
        server.setProperties(configProps);

        log.info("HSQL Server Startup sequence initiated");

        server.start();

        String portMsg = "port " + server.getPort();
        log.info("HSQL Server listening on " + portMsg);
    }

    private Configuration getConfiguration(){
    	try {
    		File configFile = this.configBeanResource.getConfigFile();
			Serializer serializer = new Persister();
			return serializer.read(Configuration.class, configFile);
		} catch (Exception e) {
			log.error("Can�t get config file", e);
			return null;
		}
    }
    
    public ConfigBeanResource getConfigBeanResource() {
		return configBeanResource;
	}

	public void setConfigBeanResource(ConfigBeanResource configBeanResource) {
		this.configBeanResource = configBeanResource;
	}
}
