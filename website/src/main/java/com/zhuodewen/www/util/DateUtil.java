package com.zhuodewen.www.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	// 2-10 15:07:33
	// 2-10 00:00:00
	/**
	 * 返回当天最早的事件 00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	//把字符串解析为日期对象
    public static Date parseDate(String dateStr){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * 返回当前最晚的事件 23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			return c.getTime();
		}
		return null;
	}

	/**
	 * 获取两个时间间隔秒数
	 */
	public static long getSecondsBetween(Date d1, Date d2) {
		return Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}



	//在原来日期上增加/减少天数
	public static Date addDays(Date current, int amount) {
		return DateUtils.addDays(current, amount);
	}

	//将字符串解析成日期
	public static Date string2Date(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
