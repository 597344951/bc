package com.zltel.broadcast.statistics.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 各类统计数据
 */
@Repository
public interface StatisticsDao {
    /**
     * 党员统计
     * @return
     */
    public List<Map<String, Integer>> partyMemberStatistics();

    /**
     * 党费缴纳统计
     * @return
     */
    public List<Map<String, Integer>> partyFeePaymentStatistics();
}
