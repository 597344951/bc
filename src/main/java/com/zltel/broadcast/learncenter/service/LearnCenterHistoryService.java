package com.zltel.broadcast.learncenter.service;

import java.util.List;

import com.zltel.broadcast.learncenter.bean.LearnCenterHistory;
import com.zltel.broadcast.learncenter.bean.LearningProgress;

/**
 * 
 * @author iamwa
 * @junit {@link LearnCenterHistoryServiceTest}
 */
public interface LearnCenterHistoryService {
    /** 查询学习记录 **/
    List<LearnCenterHistory> query(LearnCenterHistory record);

    /** 记录学习记录 **/
    void saveHistory(LearnCenterHistory record);

    /** 统计指定时间段的总积分 **/
    int sumScore(LearnCenterHistory record);

    /**
     * 学习进度
     * 
     * @junit {@link LearnCenterHistoryServiceTest#testLearningProcess}
     **/
    LearningProgress learningProcess(LearnCenterHistory record);
}
