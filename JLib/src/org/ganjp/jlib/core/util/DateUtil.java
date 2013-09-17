/**
 * DateUtil.java
 * 
 * Created by Gan Jianping on 15/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jlib.core.util;

import java.util.Calendar;

/**
 * <p>Date Utility for internal use.</p>
 *
 * @author GanJianping
 * @since 1.0
 */
public class DateUtil {
    
    public static final String getNowYyyyMmDmHhMmSs() {
    	Calendar cal = Calendar.getInstance();
    	String yearStr = cal.get(Calendar.YEAR) + "";
    	int month = cal.get(Calendar.MONTH) + 1;
    	String monthStr = month<10?"0"+month:month+"";
    	int day = cal.get(Calendar.DAY_OF_MONTH);
    	String dayStr = day<10 ? "0"+day : day+"";
    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	String hourStr = hour<10 ? "0"+hour : hour+"";
    	int minute = cal.get(Calendar.MINUTE);
    	String minuteStr = minute<10 ? "0"+minute : minute+"";
    	int second = cal.get(Calendar.SECOND);
    	String secondStr = second<10 ? "0"+second : second+"";
    	return yearStr+monthStr+dayStr+hourStr+minuteStr+secondStr;
    }
    
    public static final String getDdMmYYYYHhMmSsFormate(long time) {
    	if (time>0) {
    		Calendar cal = Calendar.getInstance();
        	cal.setTimeInMillis(time);
        	String yearStr = cal.get(Calendar.YEAR) + "";
        	int month = cal.get(Calendar.MONTH) + 1;
        	String monthStr = month<10?"0"+month:month+"";
        	int day = cal.get(Calendar.DAY_OF_MONTH);
        	String dayStr = day<10 ? "0"+day : day+"";
        	int hour = cal.get(Calendar.HOUR_OF_DAY);
        	String hourStr = hour<10 ? "0"+hour : hour+"";
        	int minute = cal.get(Calendar.MINUTE);
        	String minuteStr = minute<10 ? "0"+minute : minute+"";
        	int second = cal.get(Calendar.SECOND);
        	String secondStr = second<10 ? "0"+second : second+"";
        	return dayStr + "/" + monthStr + "/" + yearStr  + " " + hourStr + ":" + minuteStr + ":" +secondStr;
    	} else {
    		return "";
    	}
    }
    
}
