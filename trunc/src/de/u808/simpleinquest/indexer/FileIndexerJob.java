package de.u808.simpleinquest.indexer;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.indexer.impl.UpdateIndexFileProcessor;
import de.u808.simpleinquest.util.DirectoryTraverser;
import de.u808.simpleinquest.util.InvalidArgumentException;

public class FileIndexerJob implements Job {

	Log log = LogFactory.getLog(this.getClass());

	public FileIndexerJob() {

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
						new UpdateIndexFileProcessor(),
						DirectoryTraverser.TraversalMode.JustDirectories)
						.startTraversal();
			} catch (InvalidArgumentException e) {
				log.error("Error during directory traversal", e);
			}
		}
	}

}
