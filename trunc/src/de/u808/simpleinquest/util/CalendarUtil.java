package de.u808.simpleinquest.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtil {
	
	private Calendar calendar = null;
	
	public CalendarUtil (){
		calendar = GregorianCalendar.getInstance();
	}
	
	public void setActualDate(Date date){
		this.calendar.setTime(date);
	}
	
	public Date getFirstDateOfActualJear(){
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		calendar.setTime(resetFields(calendar.getTime()));
		return calendar.getTime();
	}
	
	public Date getFirstDateOfActualMonth(){
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.setTime(resetFields(calendar.getTime()));
		return calendar.getTime();
	}
	
	public Date getFirstDateOfActualWeek(){
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.setTime(resetFields(calendar.getTime()));
		return calendar.getTime();
	}
	
	public Date getLastDateOfActualJear(){
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getMaximum(Calendar.DAY_OF_YEAR));
		calendar.setTime(resetFields(calendar.getTime()));
		return calendar.getTime();
	}
	
	public Date getLastDateOfActualMonth(){
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.setTime(resetFields(calendar.getTime()));
		return calendar.getTime();
	}
	
	public Date getLastDateOfActualWeek(){
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}
	
	public static Date getToday(){
		return resetFields(new Date());
	}
	
	private static Date resetFields(Date date){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
