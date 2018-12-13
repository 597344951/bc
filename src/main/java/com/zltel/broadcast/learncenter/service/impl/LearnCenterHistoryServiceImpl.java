package com.zltel.broadcast.learncenter.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.learncenter.bean.LearnCenterHistory;
import com.zltel.broadcast.learncenter.bean.LearningProgress;
import com.zltel.broadcast.learncenter.dao.LearnCenterHistoryMapper;
import com.zltel.broadcast.learncenter.service.LearnCenterHistoryService;
import com.zltel.broadcast.learncenter.utils.QuarterUtil;

@Service
public class LearnCenterHistoryServiceImpl implements LearnCenterHistoryService {
    @Resource
    private LearnCenterHistoryMapper dao;
    /** 每天最多获取积分数 **/
    private static final int DAY_MAX_SCORE = 50;
    /** 季度需要获取分数 **/
    private static final int QUARTER_MAX_SCORE = 1000;

    private static final int PASSAGE_SCORE = 10;
    private static final int VIDEO_SCORE = 20;
    private static final int OTHER_SCORE = 10;


    @Override
    public List<LearnCenterHistory> query(LearnCenterHistory record) {
        return this.dao.query(record);
    }


    @Override
    public void saveHistory(LearnCenterHistory record) {
        int ds = getTodayScore(record);
        if (ds >= DAY_MAX_SCORE) RRException.makeThrow("当日获取积分数已达上限");

        // 记录积分
        this.dao.insertSelective(record);
    }

    /** 查询今天积分 **/
    private int getTodayScore(LearnCenterHistory record) {
        // 查询今天积分
        LearnCenterHistory t = new LearnCenterHistory();
        t.setUserId(record.getUserId());
        t.setTime(new Date());
        return this.sumScore(t);
    }

    /** 查询本季度积分 **/
    private int getQuarterScore(LearnCenterHistory record) {
        LearnCenterHistory t = new LearnCenterHistory();
        t.setUserId(record.getUserId());
        t.setFrom(QuarterUtil.getCurrentQuarterStartTime());
        t.setTo(QuarterUtil.getCurrentQuarterEndTime());
        return this.sumScore(t);
    }


    @Override
    public int sumScore(LearnCenterHistory record) {
        return this.dao.sumScore(record);
    }


    @Override
    public LearningProgress learningProcess(LearnCenterHistory record) {
        LearningProgress lp = new LearningProgress();
        lp.setDayScores(this.getTodayScore(record));
        lp.setDayTotalScores(DAY_MAX_SCORE);

        lp.setQuarterScores(this.getQuarterScore(record));
        lp.setQuarterTotalScores(QUARTER_MAX_SCORE);
        lp.setQuarterDayLeaves(QuarterUtil.getCurrentQuarterLeaveDays());
        lp.setQuarterDayTotal(QuarterUtil.getCurrentQuarterTotalDays());

        lp.setPassageScore(PASSAGE_SCORE);
        lp.setVideoScore(VIDEO_SCORE);
        lp.setOtherScore(OTHER_SCORE);
        return lp;
    }


}
