package de.u808.simpleinquest.web.tasks;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import de.u808.simpleinquest.service.scheduler.SchedulerManager;

public class TaskInfoController extends AbstractController{
	
	private Scheduler scheduler;
	
	private SchedulerManager schedulerManager;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("JSONTasksState", "tasks", schedulerManager.getTaskInfoModelList());
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setSchedulerManager(SchedulerManager schedulerManager) {
		this.schedulerManager = schedulerManager;
	}

}
