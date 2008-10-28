package de.u808.simpleinquest.web.tasks;

public class TaskInfoModel {
	
	private boolean active = false;
	
	private String statusMessage;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
