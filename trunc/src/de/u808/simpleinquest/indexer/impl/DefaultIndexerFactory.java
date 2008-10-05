package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.Indexer;
import de.u808.simpleinquest.indexer.IndexerFactory;
import eu.medsea.util.MimeUtil;

public class DefaultIndexerFactory implements IndexerFactory{
	
	static Logger log = Logger.getLogger(DefaultIndexerFactory.class);
	protected static DefaultIndexerFactory factory = null;
	protected MSWordIndexer poiHelper = new MSWordIndexer();
	private Map<String, Indexer> indexerMap = null;
	
	private DefaultIndexerFactory (){
		this.initIndexerMap();
	}
	
	private void initIndexerMap(){
		Map<String, String> mimeTypeIndexerMap = SystemConfig.getInstance().getConfiguration().getIndexerConfiguration().getMimeTypeIndexerMap();
		this.indexerMap = new LinkedHashMap<String, Indexer>();
		for( String typeKey : mimeTypeIndexerMap.keySet()){
			String indexerClassName = mimeTypeIndexerMap.get(typeKey);
			try {
				Class indexerClass = Class.forName(indexerClassName);
				if (indexerClass.getClass().isInstance(Class.forName("de.u808.simpleinquest.indexer.Indexer"))) {
					Constructor<Indexer> ctor = indexerClass.getConstructor();
					Indexer indexer = ctor.newInstance();
					this.indexerMap.put(typeKey, indexer);
				}
			} catch (ClassNotFoundException e) {
				SystemConfig.getInstance().addErrorEntry("Indexer class " + indexerClassName + " not found in classpath.", e);
			} catch (SecurityException e) {
				SystemConfig.getInstance().addErrorEntry("SecurityException while instantiate class " + indexerClassName, e);
			} catch (NoSuchMethodException e) {
				SystemConfig.getInstance().addErrorEntry("NoSuchMethodException while instantiate class " + indexerClassName, e);
			} catch (IllegalArgumentException e) {
				SystemConfig.getInstance().addErrorEntry("IllegalArgumentException while instantiate class " + indexerClassName, e);
			} catch (InstantiationException e) {
				SystemConfig.getInstance().addErrorEntry("InstantiationException while instantiate class " + indexerClassName, e);
			} catch (IllegalAccessException e) {
				SystemConfig.getInstance().addErrorEntry("IllegalAccessException while instantiate class " + indexerClassName, e);
			} catch (InvocationTargetException e) {
				SystemConfig.getInstance().addErrorEntry("InvocationTargetException while instantiate class " + indexerClassName, e);
			}
			
		}
	}
	
	public static DefaultIndexerFactory getInstance(){
		if (factory == null) factory = new DefaultIndexerFactory();
		return factory;
	}

	@Override
	public Indexer getIndexer(File file) throws IOException {
		String mimeType = MimeUtil.getMimeType(file);
		return indexerMap.get(mimeType);
	}

}
