package com.zltel.broadcast.plansuggest.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.plansuggest.bean.EventPlanSuggests;

public interface EventPlanSuggestsMapper extends BaseDao<EventPlanSuggests> {
    int deleteByPrimaryKey(Integer sugId);

    int insert(EventPlanSuggests record);

    int insertSelective(EventPlanSuggests record);

    EventPlanSuggests selectByPrimaryKey(Integer sugId);

    int updateByPrimaryKeySelective(EventPlanSuggests record);

    int updateByPrimaryKey(EventPlanSuggests record);
}
