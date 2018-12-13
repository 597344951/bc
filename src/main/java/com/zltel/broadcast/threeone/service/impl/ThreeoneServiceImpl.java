package com.zltel.broadcast.threeone.service.impl;

import com.zltel.broadcast.threeone.bean.ThreeoneLearnedWithBLOBs;
import com.zltel.broadcast.threeone.bean.ThreeoneSummaryWithBLOBs;
import com.zltel.broadcast.threeone.dao.ThreeoneLearnedMapper;
import com.zltel.broadcast.threeone.dao.ThreeoneSummaryMapper;
import com.zltel.broadcast.threeone.service.ThreeoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThreeoneServiceImpl implements ThreeoneService {
    @Autowired
    private ThreeoneSummaryMapper threeoneSummaryMapper;
    @Autowired
    private ThreeoneLearnedMapper threeoneLearnedMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addSummary(ThreeoneSummaryWithBLOBs summary) {
        threeoneSummaryMapper.insertSelective(summary);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    public void addLearned(ThreeoneLearnedWithBLOBs learned) {
        threeoneLearnedMapper.insertSelective(learned);
    }

    @Override
    public List<ThreeoneSummaryWithBLOBs> querySummary(int scheduleId) {
        return threeoneSummaryMapper.selectBySchedule(scheduleId);
    }

    @Override
    public List<ThreeoneLearnedWithBLOBs> queryLearned(int scheduleId, int userId) {
        return threeoneLearnedMapper.selectByScheduleAndUser(scheduleId, userId);
    }
}
