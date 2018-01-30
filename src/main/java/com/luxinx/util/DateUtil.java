package com.luxinx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getCurrentStr(String dateformat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		Date d = new Date();
		String nowdate = sdf.format(d);
		return nowdate;
	}
	
	public static Date getCurrentDate(){
		return new Date();
	}
	
	public static Date getStrToDate(String dateStr,String dateformat) {
		Date parmadate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
			parmadate = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parmadate;
	}
	
	
	public static String getThisYear(){
		return getYear(0);
	}
	
	public static String getYear(int yeardeviation){

		int  thisyear  = new Date().getYear();
		String deviation = (thisyear+yeardeviation)+"";
		 String strduo = deviation.substring(1);
		return strduo;
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.getYear(0));
	}
	
}
