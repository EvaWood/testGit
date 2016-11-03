package com.jinda.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CommonTools {

	public static String getFirstDay() {
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		Date date=calendar.getTime();
		
		GregorianCalendar gregorianCalendar=new GregorianCalendar();
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,1);
		
		String FirstDate=simpleDateFormat.format(gregorianCalendar.getTime());
		
		return FirstDate;
		
	}
	
	public static String getLastDay() {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		
		return new StringBuffer().append(simpleDateFormat.format(calendar.getTime())).append(" 23:59:59").toString();
		
	}
}
