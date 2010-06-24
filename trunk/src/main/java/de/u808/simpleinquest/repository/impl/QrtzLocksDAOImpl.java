package de.u808.simpleinquest.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.domain.QrtzLocks;
import de.u808.simpleinquest.repository.QrtzLocksDAO;

public class QrtzLocksDAOImpl extends GenericHibernateDAO<QrtzLocks, Long>implements QrtzLocksDAO{

	private static final Log log = LogFactory.getLog(QrtzLocksDAOImpl.class);
}
