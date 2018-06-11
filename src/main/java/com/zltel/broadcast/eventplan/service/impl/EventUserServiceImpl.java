package com.zltel.broadcast.eventplan.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.eventplan.bean.EventList;
import com.zltel.broadcast.eventplan.bean.EventUser;
import com.zltel.broadcast.eventplan.dao.EventListMapper;
import com.zltel.broadcast.eventplan.dao.EventUserMapper;
import com.zltel.broadcast.eventplan.service.EventUserService;

@Service
public class EventUserServiceImpl extends BaseDaoImpl<EventUser> implements EventUserService {

    @Resource
    private EventUserMapper eventUserMapper;
    
    @Resource
    private EventListMapper eventListMapper;

    @Override
    public BaseDao<EventUser> getInstince() {
        return this.eventUserMapper;
    }

    @Override
    @Transactional
    public void saveUserEvent(EventUser eu) {
      //周期性事件
        if("cycle".equals(eu.getEvent_type())){
            //插入 用户 计划表中
            eventUserMapper.insertSelective(eu);
        }
        //插入当前 用户 事件列表
        EventList el = EventList.from(eu);
        el.setEventId(null);
        el.setSource("user");
        this.eventListMapper.insertSelective(el);
    }



}
