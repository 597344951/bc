package com.zltel.broadcast.planjoin.dao;

import java.util.List;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.planjoin.bean.ActivityRegistration;
import com.zltel.broadcast.planjoin.bean.UserPlanJoin;

public interface ActivityRegistrationMapper extends BaseDao<ActivityRegistration> {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityRegistration record);

    int insertSelective(ActivityRegistration record);

    ActivityRegistration selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityRegistration record);

    int updateByPrimaryKey(ActivityRegistration record);

    /** 查询用户已报名的活动id **/
    List<Integer> queryRegistedEventPlanId(UserPlanJoin record, Pager pager);

    /** 查询用户可参加活动id **/
    List<Integer> queryCanJoinEventPlanId(UserPlanJoin record, Pager pager);

    /** 查询用户参加的 已停止活动 **/
    List<Integer> queryRegistStopedEventPlanId(UserPlanJoin record, Pager pager);
}
