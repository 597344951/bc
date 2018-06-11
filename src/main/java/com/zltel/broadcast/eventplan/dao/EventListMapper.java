package com.zltel.broadcast.eventplan.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventList;

public interface EventListMapper extends BaseDao<EventList>{
    int deleteByPrimaryKey(Integer eventId);

    int insert(EventList record);

    int insertSelective(EventList record);

    EventList selectByPrimaryKey(Integer eventId);

    int updateByPrimaryKeySelective(EventList record);

    int updateByPrimaryKey(EventList record);

    List<EventList> queryForList(EventList record);

    List<EventList> queryInTime(EventList record);
}
