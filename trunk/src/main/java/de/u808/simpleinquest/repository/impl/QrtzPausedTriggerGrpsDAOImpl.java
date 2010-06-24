package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzPausedTriggerGrps;
import de.u808.simpleinquest.repository.QrtzPausedTriggerGrpsDAO;

public class QrtzPausedTriggerGrpsDAOImpl extends GenericHibernateDAO<QrtzPausedTriggerGrps, Long> implements QrtzPausedTriggerGrpsDAO{

	private static final Log log = LogFactory
			.getLog(QrtzPausedTriggerGrpsDAOImpl.class);
}
