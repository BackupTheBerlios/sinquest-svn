package de.u808.simpleinquest.config;

import java.io.File;
import java.util.Properties;

public abstract class ConfigurationUtil {

    public static File getIndexDir(Properties systemProperties) {
        File homeDir = getHomeDir(systemProperties);
        if (homeDir != null) {
            return new File(homeDir, ConfigurationConstants.SIMPLE_INQUEST_INDEX_DIR_NAME);
        } else {
            return null;
        }
    }

    public static File getHomeDir(Properties systemProperties) {
        if (systemProperties.containsKey(ConfigurationConstants.SIMPLE_INQUEST_HOME_DIR)) {
            return new File(systemProperties.getProperty(ConfigurationConstants.SIMPLE_INQUEST_HOME_DIR));
        } else {
            return null;
        }
    }

}
