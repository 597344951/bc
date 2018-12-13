package com.zltel.broadcast.learncenter.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.broadcast.common.exception.RRException;

/**
 * 季度计算工具类
 * 
 * @author iamwa
 * @junit {@link QuarterUtilTest}
 */
public class QuarterUtil {

    public static final Logger log = LoggerFactory.getLogger(QuarterUtil.class);

    private QuarterUtil() {}

    /**
     * 获取 当前年、半年、季度、月、日、小时 开始结束时间
     */
    private static SimpleDateFormat shortSdf() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private static SimpleDateFormat longHourSdf() {
        return new SimpleDateFormat("yyyy-MM-dd HH");
    }

    private static SimpleDateFormat longSdf() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public static Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf().parse(shortSdf().format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf().parse(shortSdf().format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时间
     *
     * @return
     */
    public static Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf().parse(shortSdf().format(now));
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 获得本天的结束时间
     *
     * @return
     */
    public static Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf().parse(shortSdf().format(now) + " 23:59:59");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 获得本小时的开始时间
     *
     * @return
     */
    public static Date getCurrentHourStartTime() {
        Date now = new Date();
        try {
            now = longHourSdf().parse(longHourSdf().format(now));
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 获得本小时的结束时间
     *
     * @return
     */
    public static Date getCurrentHourEndTime() {
        Date now = new Date();
        try {
            now = longSdf().parse(longHourSdf().format(now) + ":59:59");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 获得本月的开始时间
     *
     * @return
     */
    public static Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf().parse(shortSdf().format(c.getTime()));
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 本月的结束时间
     *
     * @return
     */
    public static Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf().parse(shortSdf().format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 当前年的开始时间
     *
     * @return
     */
    public static Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf().parse(shortSdf().format(c.getTime()));
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 当前年的结束时间
     *
     * @return
     */
    public static Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf().parse(shortSdf().format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12) c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf().parse(shortSdf().format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf().parse(shortSdf().format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 获取前/后半年的开始时间
     *
     * @return
     */
    public static Date getHalfYearStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf().parse(shortSdf().format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;

    }

    /**
     * 获取前/后半年的结束时间
     *
     * @return
     */
    public static Date getHalfYearEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf().parse(shortSdf().format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            throw new RRException(e.getMessage());
        }
        return now;
    }

    /**
     * 获取本季度剩余天数
     * 
     * @return
     */
    public static int getCurrentQuarterLeaveDays() {
        Date end = getCurrentQuarterEndTime();
        long r = TimeUnit.MILLISECONDS.toDays(end.getTime() - System.currentTimeMillis());
        return (int) r;
    }

    /** 获取本季度总天数 **/
    public static int getCurrentQuarterTotalDays() {
        Date end = getCurrentQuarterEndTime();
        Date start = getCurrentQuarterStartTime();
        long r = TimeUnit.MILLISECONDS.toDays(end.getTime() - start.getTime());
        return (int) r;
    }
}
