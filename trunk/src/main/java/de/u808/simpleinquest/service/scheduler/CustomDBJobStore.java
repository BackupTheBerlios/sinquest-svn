package de.u808.simpleinquest.service.scheduler;

import java.util.List;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.core.SchedulingContext;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.JobStore;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;

import de.u808.simpleinquest.repository.QrtzCalendarsDAO;
import de.u808.simpleinquest.repository.QrtzJobDetailsDAO;

public class CustomDBJobStore implements JobStore {
	
	@Autowired
	private QrtzCalendarsDAO calendarDAO;
	
	@Autowired
	private QrtzJobDetailsDAO jobDetailsDAO;

	public Trigger acquireNextTrigger(SchedulingContext schedulingContext, long arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getCalendarNames(SchedulingContext schedulingContext)
			throws JobPersistenceException {
		List<String> calendarNames = calendarDAO.getCalendarNames();
		if(calendarNames != null){
			return calendarNames.toArray(new String[]{});
		} else {
			return null;
		}
	}

	public String[] getJobGroupNames(SchedulingContext schedulingContext)
			throws JobPersistenceException {
		List<String> jobGroupNames = jobDetailsDAO.getJobGroupNames();
		if(jobGroupNames != null){
			return jobGroupNames.toArray(new String [] {});
		} else {
			return null;
		}
	}

	public String[] getJobNames(SchedulingContext schedulingContext, String arg1)
			throws JobPersistenceException {
		List<String> jobNames = jobDetailsDAO.getJobNames();
		if(jobNames != null){
			return jobNames.toArray(new String [] {});
		} else {
			return null;
		}
	}

	public int getNumberOfCalendars(SchedulingContext schedulingContext)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfJobs(SchedulingContext arg0)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfTriggers(SchedulingContext arg0)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set getPausedTriggerGroups(SchedulingContext arg0)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getTriggerGroupNames(SchedulingContext arg0)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getTriggerNames(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTriggerState(SchedulingContext arg0, String arg1, String arg2)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Trigger[] getTriggersForJob(SchedulingContext arg0, String arg1,
			String arg2) throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void initialize(ClassLoadHelper arg0, SchedulerSignaler arg1)
			throws SchedulerConfigException {
		// TODO Auto-generated method stub

	}

	public void pauseAll(SchedulingContext arg0) throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void pauseJob(SchedulingContext arg0, String arg1, String arg2)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void pauseJobGroup(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void pauseTrigger(SchedulingContext arg0, String arg1, String arg2)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void pauseTriggerGroup(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void releaseAcquiredTrigger(SchedulingContext arg0, Trigger arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public boolean removeCalendar(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeJob(SchedulingContext arg0, String arg1, String arg2)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeTrigger(SchedulingContext arg0, String arg1,
			String arg2) throws JobPersistenceException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean replaceTrigger(SchedulingContext arg0, String arg1,
			String arg2, Trigger arg3) throws JobPersistenceException {
		// TODO Auto-generated method stub
		return false;
	}

	public void resumeAll(SchedulingContext arg0)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void resumeJob(SchedulingContext arg0, String arg1, String arg2)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void resumeJobGroup(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void resumeTrigger(SchedulingContext arg0, String arg1, String arg2)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void resumeTriggerGroup(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public Calendar retrieveCalendar(SchedulingContext arg0, String arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public JobDetail retrieveJob(SchedulingContext arg0, String arg1,
			String arg2) throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public Trigger retrieveTrigger(SchedulingContext arg0, String arg1,
			String arg2) throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void schedulerStarted() throws SchedulerException {
		try {
			recoverJobs();
		} catch (SchedulerException se) {
			throw new SchedulerConfigException(
					"Failure occured during job recovery.", se);
		}
		// TODO check if i need a MisfireHandler
		// misfireHandler = new MisfireHandler();
		// misfireHandler.initialize();
	}

	private void recoverJobs() throws SchedulerException{
//		         try {
//			             // update inconsistent job states
//			 int rows = getDelegate().updateTriggerStatesFromOtherStates(conn,
//			                     STATE_WAITING, STATE_ACQUIRED, STATE_BLOCKED);
//			
//			             rows += getDelegate().updateTriggerStatesFromOtherStates(conn,
//			                         STATE_PAUSED, STATE_PAUSED_BLOCKED, STATE_PAUSED_BLOCKED);
//			             
//			             getLog().info(
//			                     "Freed " + rows
//			                             + " triggers from 'acquired' / 'blocked' state.");
//			
//			             // clean up misfired jobs
//			 recoverMisfiredJobs(conn, true);
//			             
//			             // recover jobs marked for recovery that were not fully executed
//			 Trigger[] recoveringJobTriggers = getDelegate()
//			                     .selectTriggersForRecoveringJobs(conn);
//			             getLog()
//			                     .info(
//			                             "Recovering "
//			                                     + recoveringJobTriggers.length
//			                                     + " jobs that were in-progress at the time of the last shut-down.");
//			
//			             for (int i = 0; i < recoveringJobTriggers.length; ++i) {
//			                 if (jobExists(conn, recoveringJobTriggers[i].getJobName(),
//			                         recoveringJobTriggers[i].getJobGroup())) {
//			                     recoveringJobTriggers[i].computeFirstFireTime(null);
//			                     storeTrigger(conn, null, recoveringJobTriggers[i], null, false,
//			                             STATE_WAITING, false, true);
//			                 }
//			             }
//			             getLog().info("Recovery complete.");
//			
//			             // remove lingering 'complete' triggers...
//			 Key[] ct = getDelegate().selectTriggersInState(conn, STATE_COMPLETE);
//			             for(int i=0; ct != null && i < ct.length; i++) {
//			                 removeTrigger(conn, null, ct[i].getName(), ct[i].getGroup());
//			             }
//			             getLog().info(
//			                 "Removed " + ct.length
//			                 + " 'complete' triggers.");
//			             
//			             // clean up any fired trigger entries
//			 int n = getDelegate().deleteFiredTriggers(conn);
//			             getLog().info("Removed " + n + " stale fired job entries.");
//			         } catch (JobPersistenceException e) {
//			             throw e;
//			         } catch (Exception JavaDoc e) {
//			             throw new JobPersistenceException("Couldn't recover jobs: "
//			                     + e.getMessage(), e);
//			         }

	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

	public void storeCalendar(SchedulingContext arg0, String arg1,
			Calendar arg2, boolean arg3, boolean arg4)
			throws ObjectAlreadyExistsException, JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void storeJob(SchedulingContext arg0, JobDetail arg1, boolean arg2)
			throws ObjectAlreadyExistsException, JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void storeJobAndTrigger(SchedulingContext arg0, JobDetail arg1,
			Trigger arg2) throws ObjectAlreadyExistsException,
			JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public void storeTrigger(SchedulingContext arg0, Trigger arg1, boolean arg2)
			throws ObjectAlreadyExistsException, JobPersistenceException {
		// TODO Auto-generated method stub

	}

	public boolean supportsPersistence() {
		// TODO Auto-generated method stub
		return false;
	}

	public TriggerFiredBundle triggerFired(SchedulingContext arg0, Trigger arg1)
			throws JobPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void triggeredJobComplete(SchedulingContext arg0, Trigger arg1,
			JobDetail arg2, int arg3) throws JobPersistenceException {
		// TODO Auto-generated method stub

	}

}
