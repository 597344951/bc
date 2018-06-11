package com.zltel.broadcast.eventplan.dao;

import com.zltel.broadcast.eventplan.bean.EventSystem;

public interface EventSystemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EventSystem record);

    int insertSelective(EventSystem record);

    EventSystem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EventSystem record);

    int updateByPrimaryKey(EventSystem record);
}