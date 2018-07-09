package com.zltel.broadcast.eventplan.service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;

public interface EventPlanService extends BaseDao<EventPlanInfo>{

    public void save(EventPlanInfo eventplan);
}
