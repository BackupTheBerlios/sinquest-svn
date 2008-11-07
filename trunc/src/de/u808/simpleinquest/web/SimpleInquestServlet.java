package de.u808.simpleinquest.web;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import de.u808.simpleinquest.config.SystemConfig;
import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.service.UserManager;
import de.u808.simpleinquest.service.scheduler.SchedulerManager;
import de.u808.simpleinquest.task.FileIndexerJob;

public class SimpleInquestServlet extends DispatcherServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(SimpleInquestServlet.class);
	//private Scheduler scheduler;

	public void init(ServletConfig config) throws ServletException {
		log.info("------------------------Initializeing Simple Inquest------------------------");
		super.init(config);
		this.loadSystemConfig();
		this.initQuarz();
		log.info("--------------------------Initializeing completed---------------------------");
	}

	private void loadSystemConfig(){
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		ConfigBeanResource configBean = (ConfigBeanResource) context.getBean("configBeanResource");
		configBean.getSystemConfig();
				
		if(SystemConfig.getInstance().isSystemConfiguredCorectly()){
			log.info("Configuration successfuly loaded ");
			log.info(SystemConfig.getInstance().getConfiguration());
			this.testAndInitDB();
		}
		else{
			List<String> errors = SystemConfig.getInstance().getConfigurationErrors();
			log.error(errors.size() + " configuration errors:");
			int index = 0;
			for(String error : errors){
				index = index++;
				log.error("Configuration error " + index + " : " + error);
			}
		}			
	}
	
	private void testAndInitDB() {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		UserManager userManager = (UserManager) context.getBean("userManager");
		User adminUser = userManager.fetchUser("Admin");
		if(adminUser == null){
			adminUser = new User();
			adminUser.setUsername("Admin");
			adminUser.setPassword("Admin");
			userManager.makePersistent(adminUser);
		}
	}

	private void initQuarz(){
        try {
        	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        	Scheduler scheduler = (Scheduler)context.getBean("quarz");
        	
//            scheduler = StdSchedulerFactory.getDefaultScheduler();
//			// and start it off
//            scheduler.start();

            //FileIndexer Task
            //for testing
//            CronTrigger cronTrigger = new CronTrigger("FileIndexerTrigger", "IndexerGroup");
//            cronTrigger.setCronExpression("0 29 1 ? * *");
//            cronTrigger.setName(FILE_INDEXER_TRIGGER);
//            cronTrigger.setGroup(INDEXER_GROUP);
            
            //Trigger trigger = TriggerUtils.makeSecondlyTrigger(5);
            Trigger trigger = TriggerUtils.makeDailyTrigger(SchedulerManager.FILE_INDEXER_TRIGGER, 12, 07);
            trigger.setName(SchedulerManager.FILE_INDEXER_TRIGGER);
            trigger.setGroup(SchedulerManager.INDEXER_GROUP);
            
            JobDetail jobDetail = new JobDetail("FileIndexerJob",
            		SchedulerManager.INDEXER_GROUP,
                    FileIndexerJob.class);
            scheduler.scheduleJob(jobDetail, trigger);
           // schedulerFactoryBean.setJobDetails(jobs);
            
        } catch (SchedulerException e) {
        	log.error("Error during quarz start", e);
        } 

	}

//	@Override
//	public void destroy() {
//		if(scheduler != null){
//			try {
//				scheduler.shutdown();
//			} catch (SchedulerException e) {
//				log.error("Error during quarz shutdown", e);
//			}
//		}
//		super.destroy();
//	}
	
	

}
