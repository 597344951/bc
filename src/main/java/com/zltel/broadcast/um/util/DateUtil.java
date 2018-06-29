package com.zltel.broadcast.um.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.util.StringUtil;

public class DateUtil {
    
    private static final Logger logout = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
     */
    private DateUtil() {}

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String formatDate(String format, Date strDate) {
        format = StringUtil.isEmpty(format) ? YYYY_MM_DD_HH_MM_SS : format;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return strDate == null ? null : sdf.format(strDate);
    }

    public static Date toDate(String format, String strDate) {
        format = StringUtil.isEmpty(format) ? YYYY_MM_DD_HH_MM_SS : format;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return StringUtil.isEmpty(strDate) ? null : sdf.parse(strDate);
        } catch (ParseException e) {
           logout.error("转换日期错误:{}",e.getMessage());
        }
        return null;
    }
    
    /**
     * 得到指定日期开始时间
     * @param date
     * @return
     */
    public static Date getDateOfStartTime(Date date) {
    	Calendar todayStart = Calendar.getInstance();
    	todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);    
        todayStart.set(Calendar.MINUTE, 0);    
        todayStart.set(Calendar.SECOND, 0);    
        todayStart.set(Calendar.MILLISECOND, 0);    
        return todayStart.getTime();   
    }
    
    /**
     * 得到指定日期结束时间
     * @param date
     * @return
     */
    public static Date getDateOfEndTime(Date date) {
    	Calendar todayEnd = Calendar.getInstance();    
    	todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);    
        todayEnd.set(Calendar.MINUTE, 59);    
        todayEnd.set(Calendar.SECOND, 59);    
        todayEnd.set(Calendar.MILLISECOND, 999);    
        return todayEnd.getTime();  
    }
}
