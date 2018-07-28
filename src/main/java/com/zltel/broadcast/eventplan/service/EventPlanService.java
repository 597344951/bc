package com.zltel.broadcast.eventplan.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;

public interface EventPlanService extends BaseDao<EventPlanInfo> {

    public void save(EventPlanInfo eventplan);

    /** 查询 未结束活动 **/
    List<EventPlanInfo> queryUnStop(EventPlanInfo eventplan);

    /** 更新状态信息 **/
    public void updateStatus(EventPlanInfo plan);

    /** 像用户发送投票信息 **/
    public void sendVotingToUser(EventPlanInfo plan);

    /**
     * 活动 投屏任务id回填
     * 
     * @param eventPlanId 活动任务id
     * @param pubTaskId   发布任务id
     */
    public void pubTaskIdBackFill(Integer eventPlanId, String pubTaskId);

}
