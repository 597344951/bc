package com.zltel.broadcast.eventplan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.eventplan.bean.EventList;
import com.zltel.broadcast.eventplan.dao.EventListMapper;
import com.zltel.broadcast.eventplan.service.EventListService;

@Service
public class EventListServiceImpl extends BaseDaoImpl<EventList> implements EventListService {
    
    @Resource
    private EventListMapper eventListMapper;

    @Override
    public List<EventList> queryForList(EventList record) {
        return this.eventListMapper.queryForList(record);
    }

    @Override
    public List<EventList> queryInTime(EventList record) {
        
        return this.eventListMapper.queryInTime(record);
    }

    @Override
    public BaseDao<EventList> getInstince() {
       return this.eventListMapper;
    }

}
