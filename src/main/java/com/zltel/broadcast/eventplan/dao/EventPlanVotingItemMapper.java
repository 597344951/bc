package com.zltel.broadcast.eventplan.dao;

import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItemKey;

public interface EventPlanVotingItemMapper {
    int deleteByPrimaryKey(EventPlanVotingItemKey key);

    int insert(EventPlanVotingItem record);

    int insertSelective(EventPlanVotingItem record);

    EventPlanVotingItem selectByPrimaryKey(EventPlanVotingItemKey key);

    int updateByPrimaryKeySelective(EventPlanVotingItem record);

    int updateByPrimaryKey(EventPlanVotingItem record);
}