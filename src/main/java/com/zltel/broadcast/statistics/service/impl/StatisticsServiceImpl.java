package com.zltel.broadcast.statistics.service.impl;

import com.zltel.broadcast.statistics.dao.StatisticsDao;
import com.zltel.broadcast.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsDao statisticsDao;
    @Override
    public List<Map<String, Integer>> partyMemberStatistics() {
        return statisticsDao.partyMemberStatistics();
    }

    @Override
    public List<Map<String, Integer>> partyFeePaymentStatistics() {
        return statisticsDao.partyFeePaymentStatistics();
    }
}
