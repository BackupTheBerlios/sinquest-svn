package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzTriggerListeners;
import de.u808.simpleinquest.repository.QrtzTriggerListenersDAO;

public class QrtzTriggerListenersDAOImpl extends GenericHibernateDAO<QrtzTriggerListeners, Long> implements QrtzTriggerListenersDAO{

	private static final Log log = LogFactory
			.getLog(QrtzTriggerListenersDAOImpl.class);
}
