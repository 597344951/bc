package com.zltel.broadcast.statistics.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 各项统计
 */
@Service
public interface StatisticsService {
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
