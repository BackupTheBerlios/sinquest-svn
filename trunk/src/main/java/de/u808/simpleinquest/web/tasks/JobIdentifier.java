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
