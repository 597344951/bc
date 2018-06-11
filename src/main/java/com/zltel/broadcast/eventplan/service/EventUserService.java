package com.zltel.broadcast.eventplan.service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventUser;

public interface EventUserService extends BaseDao<EventUser> {
    /**保存用户事件*/
    public void saveUserEvent(EventUser record);

}
