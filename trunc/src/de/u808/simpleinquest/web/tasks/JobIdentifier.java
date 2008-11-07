package de.u808.simpleinquest.web.tasks;

public class JobIdentifier {

	private String jobId;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getJobName(){
		if(jobId != null && jobId.contains(".")){
			int splitPos = jobId.indexOf(".");
			return jobId.substring(splitPos + 1);
		}
		else return null;
	}
	
	public String getGroupname(){
		if(jobId != null && jobId.contains(".")){
			int splitPos = jobId.indexOf(".");
			return jobId.substring(0, splitPos);
		}
		else return null;
	}
	
}
