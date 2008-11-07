package de.u808.simpleinquest.service.scheduler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import de.u808.simpleinquest.web.tasks.JobIdentifier;
import de.u808.simpleinquest.web.tasks.TaskInfoModel;

public class SchedulerManager {
	
	public static final String FILE_INDEXER_TRIGGER = "FileIndexerTrigger";
	public static final String IMMEDIATELY_FILE_INDEXER_TRIGGER = "ImmediatelyFileIndexerTrigger";
	public static final String INDEXER_GROUP = "IndexerGroup";
	
	private final static Log log = LogFactory.getLog(SchedulerManager.class);

	Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public List<TaskInfoModel> getTaskInfoModelList() {
		Map<String, TaskInfoModel> jobsMap = new LinkedHashMap<String, TaskInfoModel>();
		String[] jobGroups;
		String[] jobsInGroup;
		int i;
		int j;

		try {
			jobGroups = scheduler.getJobGroupNames();
			for (i = 0; i < jobGroups.length; i++) {
				jobsInGroup = scheduler.getJobNames(jobGroups[i]);

				for (j = 0; j < jobsInGroup.length; j++) {
					TaskInfoModel jobInfo = new TaskInfoModel();
					String jobName = jobsInGroup[j];
					String groupName = jobGroups[i];
					String fullName = groupName + "." + jobName;
					jobInfo.setId(fullName);
					jobInfo.setName(jobName);
					jobInfo.setNextExecutionDate(this.getNextExecutionDate(scheduler.getTriggersOfJob(jobsInGroup[j], jobGroups[i])));
					jobsMap.put(fullName, jobInfo);
				}
			}
			List <JobExecutionContext> runningJobs = this.scheduler.getCurrentlyExecutingJobs();
			for(JobExecutionContext jobExecutionContext : runningJobs){
				TaskInfoModel taskInfoModel = jobsMap.get(jobExecutionContext.getJobDetail().getFullName());
				if(taskInfoModel != null){
					taskInfoModel.setIsActive(true);
				}
				else{
					log.warn("JobInfoModel not found for Job " + jobExecutionContext.getJobDetail().getName());
				}
			}
		} catch (SchedulerException e) {
			log.error("Can´t create TaskInfoModel", e);
		}
		return new LinkedList<TaskInfoModel>(jobsMap.values());
	}
	
	public Date getNextExecutionDate(Trigger[] triggers){
		Date nextExecutionDate = null;
		for(Trigger trigger : triggers){
			Date nextFireTime = trigger.getNextFireTime();
			if(nextExecutionDate == null || ((nextFireTime != null) && (nextFireTime.before(nextExecutionDate)))){
				nextExecutionDate = trigger.getNextFireTime();
			}
		}
		return nextExecutionDate;
	}
	
	public void startJob(JobIdentifier jobIdentifier) throws SchedulerException{
		// Define a Trigger that will fire "now" and associate it with the existing job
		Trigger trigger = new SimpleTrigger(IMMEDIATELY_FILE_INDEXER_TRIGGER, INDEXER_GROUP, new Date());
		trigger.setJobName(jobIdentifier.getJobName());
		trigger.setJobGroup(jobIdentifier.getGroupname());

		// Schedule the trigger
		this.scheduler.scheduleJob(trigger);
	}
}
