package com.zltel.broadcast.planjoin.service;

import java.util.List;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.planjoin.bean.ActivityRegistration;
import com.zltel.broadcast.planjoin.bean.ActivitySign;
import com.zltel.broadcast.planjoin.bean.UserPlanJoin;

/**
 * 活动参与服务
 * 
 * @author wangch
 *
 */
public interface PlanJoinService {
    /**用户活动报名**/
    void registration(ActivityRegistration record);
    /**用户签到**/
    void signIn(ActivitySign sign);
    /**查询可操作活动**/
    List<EventPlanInfo> queryRelatedPlan(UserPlanJoin upj,Pager pager);
    /**查询已参加活动**/
    List<EventPlanInfo> queryJoinedPlan(UserPlanJoin upj,Pager pager);
    /**查询 参加的已结束活动**/
    List<EventPlanInfo> queryEndPlan(UserPlanJoin upj,Pager pager);
    
}
