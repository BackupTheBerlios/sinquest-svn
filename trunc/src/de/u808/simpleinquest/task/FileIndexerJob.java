package de.u808.simpleinquest.task;

import java.io.File;

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
