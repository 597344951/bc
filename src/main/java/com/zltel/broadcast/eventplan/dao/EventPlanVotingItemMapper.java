package com.zltel.broadcast.eventplan.dao;

import java.util.List;

import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItemKey;

public interface EventPlanVotingItemMapper {
    int deleteByPrimaryKey(EventPlanVotingItemKey key);

    int insert(EventPlanVotingItem record);

    int insertSelective(EventPlanVotingItem record);

    EventPlanVotingItem selectByPrimaryKey(EventPlanVotingItemKey key);

    int updateByPrimaryKeySelective(EventPlanVotingItem record);

    int updateByPrimaryKey(EventPlanVotingItem record);

    List<EventPlanVotingItem> query(EventPlanVotingItem record);

    int delete(EventPlanVotingItem record);
    /**查询组织 活动投票详情**/
    List<EventPlanVotingItem> queryVotingInfo(Integer eventPlanId);
}