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

package de.u808.simpleinquest.task;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import de.u808.simpleinquest.config.DirectoryConfiguration;
import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.impl.IndexUpdater;
import de.u808.simpleinquest.util.DirectoryTraverser;
import de.u808.simpleinquest.util.InvalidArgumentException;

public class FileIndexerJob implements Task {

	private final static Log log = LogFactory.getLog(FileIndexerJob.class);
	
	private IndexUpdater indexUpdater;
	
	private JobExecutionContext context;

	public FileIndexerJob() {
		ApplicationContext applicationContext = SystemConfig.getInstance().getApplicationContext();
		indexUpdater = (IndexUpdater) applicationContext.getBean("indexUpdater");
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		indexUpdater.setJobExecutionContext(context);
		String startMsg = "File indexer started";
		log.info(startMsg);
		context.setResult(startMsg);
		this.updateIndex();
		String endMsg = "File indexer finished";
		log.info(endMsg);
		context.setResult(endMsg);
	}

	private void updateIndex() {
		for (DirectoryConfiguration directoryConfiguration : SystemConfig.getInstance().getConfiguration()
				.getDirectoriesToIndex()) {
			String rootDir = directoryConfiguration.getPath();
			try {
				if(StringUtils.isNotEmpty(rootDir)){
					new DirectoryTraverser(new File(rootDir),
						indexUpdater,
						DirectoryTraverser.TraversalMode.JustDirectories)
						.startTraversal();
				}
				else{
					log.warn("Empty root directory string in config. Maybe the attribute \"directoriesToIndexList\" is set to a wrong value");
				}
			} catch (InvalidArgumentException e) {
				log.error("Error during directory traversal", e);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	public String getStatusMessage() {
		if(context.getResult() != null){
			return context.getResult().toString();
		}
		else return null;
	}

}
