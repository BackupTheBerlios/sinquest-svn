package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzCronTriggers;
import de.u808.simpleinquest.repository.QrtzCronTriggersDAO;

public class QrtzCronTriggersDAOImpl extends GenericHibernateDAO<QrtzCronTriggers, Long> implements QrtzCronTriggersDAO{

	private static final Log log = LogFactory
			.getLog(QrtzCronTriggersDAOImpl.class);

}
