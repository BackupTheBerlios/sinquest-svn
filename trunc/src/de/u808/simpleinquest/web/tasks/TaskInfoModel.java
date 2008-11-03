package de.u808.simpleinquest.web.tasks;

import java.util.Date;

public class TaskInfoModel {
	
	private String name;
	
	private boolean isActive = false;
	
	private String statusMessage;
	
	private Date nextExecutionDate;

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean active) {
		this.isActive = active;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getNextExecutionDate() {
		return nextExecutionDate;
	}

	public void setNextExecutionDate(Date nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}

}
