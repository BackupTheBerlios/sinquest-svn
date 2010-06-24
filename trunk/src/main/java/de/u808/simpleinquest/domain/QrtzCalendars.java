package de.u808.simpleinquest.domain;
// Generated 24.10.2009 14:00:41 by Hibernate Tools 3.2.5.Beta

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QrtzCalendars generated by hbm2java
 */
@Entity
@Table(name = "QRTZ_CALENDARS", schema = "PUBLIC")
public class QrtzCalendars implements java.io.Serializable {

	private String calendarName;
	private byte[] calendar;

	public QrtzCalendars() {
	}

	public QrtzCalendars(String calendarName, byte[] calendar) {
		this.calendarName = calendarName;
		this.calendar = calendar;
	}

	@Id
	@Column(name = "CALENDAR_NAME", unique = true, nullable = false, length = 80)
	public String getCalendarName() {
		return this.calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	@Column(name = "CALENDAR", nullable = false)
	public byte[] getCalendar() {
		return this.calendar;
	}

	public void setCalendar(byte[] calendar) {
		this.calendar = calendar;
	}

}