package de.u808.simpleinquest.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.load.Persister;

public class SimpleXMLUtil {
    
    private final static Log log = LogFactory.getLog(SimpleXMLUtil.class);

    public static Object getObject(Class clazz, File xmlFile) {
        Serializer serializer = new Persister();
        try {
            if (xmlFile != null && xmlFile.exists()) {
                return serializer.read(clazz, xmlFile);
            }            
        } catch (Exception e) {
            log.error("Can´t load system config", e);
        } 
        return null;        
    }
}
