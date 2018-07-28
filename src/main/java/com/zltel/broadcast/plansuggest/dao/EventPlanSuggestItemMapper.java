package com.zltel.broadcast.plansuggest.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.plansuggest.bean.EventPlanSuggestItem;

public interface EventPlanSuggestItemMapper extends BaseDao<EventPlanSuggestItem>{
    int deleteByPrimaryKey(Integer id);

    int insert(EventPlanSuggestItem record);

    int insertSelective(EventPlanSuggestItem record);

    EventPlanSuggestItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventPlanSuggestItem record);

    int updateByPrimaryKey(EventPlanSuggestItem record);
    
    List<EventPlanSuggestItem> selectByEventId(Integer eventId);
}