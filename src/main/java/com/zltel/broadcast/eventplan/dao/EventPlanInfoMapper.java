package com.zltel.broadcast.eventplan.dao;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;

public interface EventPlanInfoMapper extends BaseDao<EventPlanInfo>{

    int deleteByPrimaryKey(Integer eventPlanId);

    int insert(EventPlanInfo record);

    int insertSelective(EventPlanInfo record);

    EventPlanInfo selectByPrimaryKey(Integer eventPlanId);

    int updateByPrimaryKeySelective(EventPlanInfo record);

    int updateByPrimaryKeyWithBLOBs(EventPlanInfo record);

    int updateByPrimaryKey(EventPlanInfo record);
    
    /**按给定条件查**/
    List<EventPlanInfo> query(EventPlanInfo record,PageRowBounds prb);

    List<EventPlanInfo> queryForList(PageRowBounds prb);

    List<EventPlanInfo> queryUnStop(EventPlanInfo eventplan);
}