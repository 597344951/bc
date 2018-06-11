package com.zltel.broadcast.eventplan.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventList;

public interface EventListService extends BaseDao<EventList> {
    /**
     * 查询 用户 安排事件
     * @param record
     * @return
     * @junit {@link EventListServiceTest#testQueryForList()}
     */
    List<EventList> queryForList(EventList record);
    /**
     * 查询事件在 指定发生时间内的 事件
     * @param record
     * @return
     * @junit {@link EventListServiceTest#testQueryInTime()}
     */
    List<EventList> queryInTime(EventList record);
}
