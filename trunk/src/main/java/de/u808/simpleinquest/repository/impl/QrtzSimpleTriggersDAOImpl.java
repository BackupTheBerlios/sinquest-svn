package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzSimpleTriggers;
import de.u808.simpleinquest.repository.QrtzSimpleTriggersDAO;

public class QrtzSimpleTriggersDAOImpl extends GenericHibernateDAO<QrtzSimpleTriggers, Long> implements QrtzSimpleTriggersDAO{

	private static final Log log = LogFactory
			.getLog(QrtzSimpleTriggersDAOImpl.class);

}
