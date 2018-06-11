package com.zltel.broadcast.eventplan.dao;

import com.zltel.broadcast.eventplan.bean.CostPlan;

public interface CostPlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CostPlan record);

    int insertSelective(CostPlan record);

    CostPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostPlan record);

    int updateByPrimaryKey(CostPlan record);
}