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

package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import de.u808.simpleinquest.config.ConfigurationError;
import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;
import de.u808.simpleinquest.service.MimeTypeRegistry;
import de.u808.simpleinquest.web.ConfigBeanResource;

public class DefaultIndexerFactory implements IndexerFactory, InitializingBean {
	
	private static final String COMMA = ",";
	private static final String DE_U808_SIMPLEINQUEST_INDEXER_INDEXER = "de.u808.simpleinquest.indexer.Indexer";
	static Logger log = Logger.getLogger(DefaultIndexerFactory.class);
	private Map<String, Indexer> indexerMap = null;
	private ConfigBeanResource configBeanResource;
	private MimeTypeRegistry mimeTypeRegistry;
	
	private void initIndexerMap(){
		Map<String, String> mimeTypeIndexerMap = configBeanResource.getSystemConfig().getConfiguration().getIndexerConfiguration().getMimeTypeIndexerMap();
		//Map<String, String> mimeTypeIndexerMap = SystemConfig.getInstance().getConfiguration().getIndexerConfiguration().getMimeTypeIndexerMap();
		this.indexerMap = new LinkedHashMap<String, Indexer>();
		for( String typeKey : mimeTypeIndexerMap.keySet()){
			String indexerClassName = mimeTypeIndexerMap.get(typeKey);
			try {
				Class indexerClass = Class.forName(indexerClassName);
				if (indexerClass.getClass().isInstance(Class.forName(DE_U808_SIMPLEINQUEST_INDEXER_INDEXER))) {
					Constructor<Indexer> ctor = indexerClass.getConstructor();
					Indexer indexer = ctor.newInstance();
					this.indexerMap.put(typeKey, indexer);
				}
			} catch (ClassNotFoundException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("Indexer class " + indexerClassName + " not found in classpath.", ConfigurationError.Severity.WARNING, e));
			} catch (SecurityException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("SecurityException while instantiate class " + indexerClassName, ConfigurationError.Severity.WARNING, e));
			} catch (NoSuchMethodException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("NoSuchMethodException while instantiate class " + indexerClassName, ConfigurationError.Severity.WARNING, e));
			} catch (IllegalArgumentException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("IllegalArgumentException while instantiate class " + indexerClassName, ConfigurationError.Severity.WARNING, e));
			} catch (InstantiationException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("InstantiationException while instantiate class " + indexerClassName, ConfigurationError.Severity.WARNING, e));
			} catch (IllegalAccessException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("IllegalAccessException while instantiate class " + indexerClassName, ConfigurationError.Severity.WARNING, e));
			} catch (InvocationTargetException e) {
				SystemConfig.getInstance().addError(new ConfigurationError("InvocationTargetException while instantiate class " + indexerClassName, ConfigurationError.Severity.WARNING, e));
			}
			
		}
	}
	
//	public static DefaultIndexerFactory getInstance(){
//		if (factory == null) factory = new DefaultIndexerFactory();
//		return factory;
//	}

	public Indexer getIndexer(File file) throws IOException {
		String mimeType = this.mimeTypeRegistry.getMimeType(file);
		if(mimeType.contains(COMMA)){
			String[] mimeTypes= mimeType.split(COMMA);
			for(String type : mimeTypes){
				if(indexerMap.containsKey(type)){
					return indexerMap.get(type);
				}
			}
			return null;
		}
		else {
			return indexerMap.get(mimeType);
		}
	}

	public void setConfigBeanResource(ConfigBeanResource configBeanResource) {
		this.configBeanResource = configBeanResource;
	}

	public void afterPropertiesSet() throws Exception {
		this.initIndexerMap();
	}

	public void setMimeTypeRegistry(MimeTypeRegistry mimeTypeRegistry) {
		this.mimeTypeRegistry = mimeTypeRegistry;
	}
	
	public Collection<String> getMappedMimeTypes(){
		return this.indexerMap.keySet();
	}

}
