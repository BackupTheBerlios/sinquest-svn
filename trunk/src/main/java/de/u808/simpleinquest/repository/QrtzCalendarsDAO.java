package de.u808.simpleinquest.repository;

import java.util.List;

import org.quartz.JobPersistenceException;

public interface QrtzCalendarsDAO {

	public List<String> getCalendarNames() throws JobPersistenceException;
}
