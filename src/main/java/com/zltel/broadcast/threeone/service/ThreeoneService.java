package com.zltel.broadcast.threeone.service;

import com.zltel.broadcast.threeone.bean.ThreeoneLearnedWithBLOBs;
import com.zltel.broadcast.threeone.bean.ThreeoneSummaryWithBLOBs;

import java.util.List;

public interface ThreeoneService {
    public void addSummary(ThreeoneSummaryWithBLOBs summary);
    public void addLearned(ThreeoneLearnedWithBLOBs learned);
    public List<ThreeoneSummaryWithBLOBs> querySummary(int scheduleId);
    public List<ThreeoneLearnedWithBLOBs> queryLearned(int scheduleId, int userId);
}
