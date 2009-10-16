/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
