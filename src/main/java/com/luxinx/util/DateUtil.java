package com.luxinx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

	/**
	 * 获取当前时间日期
	 * @param dateformat
	 * @return
	 */
	public static String getCurrentDateStr(String dateformat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		return sdf.format(new Date()).toString();
	}

	/**
	 * 获取当前日期
	 * @return Date类型日期
	 */
	public static Date getCurrentDate(){
		return new Date();
	}

	/**
	 * 将日期字符类型转成日期型
	 * @param dateStr 日期字符串
	 * @param dateformat 日期格式
	 * @return Date类型日期
	 */
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

	/**
	 * 获取当前年份
	 * @return 2位年份字符串
	 */
	public static String getThisYear(){
		return getYear(0);
	}

	/**
	 * 根据当前年份偏移获取年份
	 * @param yeardeviation 例如当前年下一年 yeardeviation=1 ；前一年yeardeviation=-1
	 * @return 2位年份字符串
	 */
	public static String getYear(int yeardeviation){

		int  thisyear  = new Date().getYear();
		String deviation = (thisyear+yeardeviation)+"";
		 String strduo = deviation.substring(1);
		return strduo;
	}

	
}
