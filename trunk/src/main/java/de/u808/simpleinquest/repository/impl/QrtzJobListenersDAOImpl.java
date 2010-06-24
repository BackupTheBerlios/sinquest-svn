package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzJobListeners;
import de.u808.simpleinquest.repository.QrtzJobListenersDAO;

public class QrtzJobListenersDAOImpl extends GenericHibernateDAO<QrtzJobListeners, Long> implements QrtzJobListenersDAO {

	private static final Log log = LogFactory
			.getLog(QrtzJobListenersDAOImpl.class);
}
