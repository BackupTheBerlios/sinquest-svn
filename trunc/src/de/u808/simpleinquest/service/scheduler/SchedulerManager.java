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
import org.quartz.Trigger;

import de.u808.simpleinquest.web.tasks.TaskInfoModel;

public class SchedulerManager {
	
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
				System.out.println("Group: " + jobGroups[i]
						+ " contains the following jobs");
				jobsInGroup = scheduler.getJobNames(jobGroups[i]);

				for (j = 0; j < jobsInGroup.length; j++) {
					System.out.println("- " + jobsInGroup[j]);
					TaskInfoModel jobInfo = new TaskInfoModel();
					jobInfo.setName(jobsInGroup[j]);
					jobInfo.setNextExecutionDate(this.getNextExecutionDate(scheduler.getTriggersOfJob(jobsInGroup[j], jobGroups[i])));
					jobsMap.put(jobGroups[i] + "." + jobsInGroup[j], jobInfo);
				}
			}
			List <JobExecutionContext> runningJobs = this.scheduler.getCurrentlyExecutingJobs();
			for(JobExecutionContext jobExecutionContext : runningJobs){
				TaskInfoModel taskInfoModel = jobsMap.get(jobExecutionContext.getJobDetail().getFullName());
				if(taskInfoModel != null){
					taskInfoModel.setActive(true);
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
			if(nextExecutionDate == null || trigger.getNextFireTime().before(nextExecutionDate)){
				nextExecutionDate = trigger.getNextFireTime();
			}
		}
		return nextExecutionDate;
	}
}
