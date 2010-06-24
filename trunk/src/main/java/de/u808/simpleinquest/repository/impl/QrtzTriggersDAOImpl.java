package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzTriggers;
import de.u808.simpleinquest.repository.QrtzTriggersDAO;

public class QrtzTriggersDAOImpl extends GenericHibernateDAO<QrtzTriggers, Long> implements QrtzTriggersDAO{

	private static final Log log = LogFactory.getLog(QrtzTriggersDAOImpl.class);

}
