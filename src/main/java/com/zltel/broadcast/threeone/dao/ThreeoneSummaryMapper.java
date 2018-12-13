package com.zltel.broadcast.threeone.dao;

import com.zltel.broadcast.threeone.bean.ThreeoneSummary;
import com.zltel.broadcast.threeone.bean.ThreeoneSummaryWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThreeoneSummaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThreeoneSummaryWithBLOBs record);

    int insertSelective(ThreeoneSummaryWithBLOBs record);

    ThreeoneSummaryWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThreeoneSummaryWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ThreeoneSummaryWithBLOBs record);

    int updateByPrimaryKey(ThreeoneSummary record);

    List<ThreeoneSummaryWithBLOBs> selectBySchedule(@Param("scheduleId") Integer scheduleId);
}