package com.zltel.broadcast.learncenter.utils;


import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentDayEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentDayStartTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentHourEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentHourStartTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentMonthEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentMonthStartTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentQuarterEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentQuarterStartTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentWeekDayEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentWeekDayStartTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentYearEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getCurrentYearStartTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getHalfYearEndTime;
import static com.zltel.broadcast.learncenter.utils.QuarterUtil.getHalfYearStartTime;

import javax.annotation.Generated;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.BaseTests;

@Generated(value = "org.junit-tools-1.0.6")
public class QuarterUtilTest extends BaseTests {
    
    private static final Logger log = LoggerFactory.getLogger(QuarterUtilTest.class);

    @Test
    public void test() throws Exception {
        log.info("当前小时开始：{}", getCurrentHourStartTime().toString());
        log.info("当前小时结束：{}", getCurrentHourEndTime().toString());
        log.info("当前天开始：{}", getCurrentDayStartTime().toString());
        log.info("当前天时结束：{}", getCurrentDayEndTime().toString());
        log.info("当前周开始：{}", getCurrentWeekDayStartTime().toString());
        log.info("当前周结束：{}", getCurrentWeekDayEndTime().toString());
        log.info("当前月开始：{}", getCurrentMonthStartTime().toString());
        log.info("当前月结束：{}", getCurrentMonthEndTime().toString());
        log.info("当前季度开始：{}", getCurrentQuarterStartTime().toString());
        log.info("当前季度结束：{}", getCurrentQuarterEndTime().toString());
        log.info("当前半年/后半年开始：{}", getHalfYearStartTime().toString());
        log.info("当前半年/后半年结束：{}", getHalfYearEndTime().toString());
        log.info("当前年开始：{}", getCurrentYearStartTime().toString());
        log.info("当前年结束：{}", getCurrentYearEndTime().toString());
    }
    
    @Test
    public void testGetCurrentQuarterLeaveDays() {
        int days = QuarterUtil.getCurrentQuarterLeaveDays();
        log.info("剩余天数: {}",days);
    }
}
