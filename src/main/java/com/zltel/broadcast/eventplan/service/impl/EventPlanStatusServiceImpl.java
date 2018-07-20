package com.zltel.broadcast.eventplan.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.eventplan.bean.EventPlanStatus;
import com.zltel.broadcast.eventplan.dao.EventPlanStatusMapper;
import com.zltel.broadcast.eventplan.service.EventPlanStatusService;

@Service
public class EventPlanStatusServiceImpl extends BaseDaoImpl<EventPlanStatus> implements EventPlanStatusService {

    @Resource
    private EventPlanStatusMapper mapper;

    @Override
    public BaseDao<EventPlanStatus> getInstince() {
        return mapper;
    }


}
