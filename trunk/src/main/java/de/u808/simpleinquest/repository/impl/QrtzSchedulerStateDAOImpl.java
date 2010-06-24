package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzSchedulerState;
import de.u808.simpleinquest.repository.QrtzSchedulerStateDAO;

public class QrtzSchedulerStateDAOImpl extends GenericHibernateDAO<QrtzSchedulerState, Long> implements QrtzSchedulerStateDAO{

	private static final Log log = LogFactory
			.getLog(QrtzSchedulerStateDAOImpl.class);
}
