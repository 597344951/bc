package com.zltel.broadcast.eventplan.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.CostPlan;

public interface CostPlanMapper extends BaseDao<CostPlan>{
    int deleteByPrimaryKey(Integer id);

    int insert(CostPlan record);

    int insertSelective(CostPlan record);

    CostPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CostPlan record);

    int updateByPrimaryKey(CostPlan record);
    
    public List<CostPlan> selectByEventPlanId(Integer eventPlanId);
}