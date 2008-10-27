package de.u808.simpleinquest.task;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.impl.IndexUpdater;
import de.u808.simpleinquest.util.DirectoryTraverser;
import de.u808.simpleinquest.util.InvalidArgumentException;

public class FileIndexerJob implements Task {

	private final static Log log = LogFactory.getLog(FileIndexerJob.class);
	
	private IndexUpdater indexUpdater;

	public FileIndexerJob(ApplicationContext applicationContext) {
		indexUpdater = (IndexUpdater) applicationContext.getBean("indexUpdater");
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info("File indexer started");
		this.updateIndex();
		log.info("File indexer finished");
	}

	private void updateIndex() {
		for (String rootDir : SystemConfig.getInstance().getConfiguration()
				.getDirectoriesToIndexList()) {
			try {
				new DirectoryTraverser(new File(rootDir),
						indexUpdater,
						DirectoryTraverser.TraversalMode.JustDirectories)
						.startTraversal();
			} catch (InvalidArgumentException e) {
				log.error("Error during directory traversal", e);
			}
		}
	}

	public String getStatusMessage() {
		return indexUpdater.getStatusMessage();
	}

}
