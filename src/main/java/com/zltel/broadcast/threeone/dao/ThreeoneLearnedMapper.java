package com.zltel.broadcast.threeone.dao;

import com.zltel.broadcast.threeone.bean.ThreeoneLearned;
import com.zltel.broadcast.threeone.bean.ThreeoneLearnedWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThreeoneLearnedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThreeoneLearnedWithBLOBs record);

    int insertSelective(ThreeoneLearnedWithBLOBs record);

    ThreeoneLearnedWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThreeoneLearnedWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ThreeoneLearnedWithBLOBs record);

    int updateByPrimaryKey(ThreeoneLearned record);

    public List<ThreeoneLearnedWithBLOBs> selectByScheduleAndUser(@Param("scheduleId") Integer scheduleId, @Param("userId") Integer userId);
}