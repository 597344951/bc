package com.zltel.broadcast.eventplan.dao;

import com.zltel.broadcast.eventplan.bean.EventPlanInfo;

public interface EventPlanInfoMapper {
    int deleteByPrimaryKey(Integer eventPlanId);

    int insert(EventPlanInfo record);

    int insertSelective(EventPlanInfo record);

    EventPlanInfo selectByPrimaryKey(Integer eventPlanId);

    int updateByPrimaryKeySelective(EventPlanInfo record);

    int updateByPrimaryKeyWithBLOBs(EventPlanInfo record);

    int updateByPrimaryKey(EventPlanInfo record);
}