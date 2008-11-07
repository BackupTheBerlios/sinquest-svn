package de.u808.simpleinquest.web.tasks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import de.u808.simpleinquest.service.scheduler.SchedulerManager;

public class JobCommandController extends AbstractCommandController {
	
	private final static Log log = LogFactory.getLog(JobCommandController.class);
	
	private SchedulerManager schedulerManager;
	
	public JobCommandController(){
		setCommandClass(JobIdentifier.class);
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException bindException)
			throws Exception {
		JobIdentifier jobIdentifier = (JobIdentifier) command;
		log.info("Test and starting Job");
		try{
			schedulerManager.startJob(jobIdentifier);
		} catch (SchedulerException e){
			response.sendError(500);
		}
		return null;
	}

	public void setSchedulerManager(SchedulerManager schedulerManager) {
		this.schedulerManager = schedulerManager;
	}

}
