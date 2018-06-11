package com.zltel.broadcast.eventplan.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventUser;

public interface EventUserMapper  extends BaseDao<EventUser>{
    int deleteByPrimaryKey(Integer id);

    int insert(EventUser record);

    int insertSelective(EventUser record);

    EventUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventUser record);

    int updateByPrimaryKey(EventUser record);
}