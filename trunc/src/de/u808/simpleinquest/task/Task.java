package de.u808.simpleinquest.task;

import org.quartz.Job;

public interface Task extends Job{
	
	public String getStatusMessage();

}
