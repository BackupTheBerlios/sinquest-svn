package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzBlobTriggers;
import de.u808.simpleinquest.repository.QrtzBlobTriggersDAO;

public class QrtzBlobTriggersDAOImpl extends GenericHibernateDAO <QrtzBlobTriggers, Long>implements QrtzBlobTriggersDAO{

	private static final Log log = LogFactory
			.getLog(QrtzBlobTriggersDAOImpl.class);


}
