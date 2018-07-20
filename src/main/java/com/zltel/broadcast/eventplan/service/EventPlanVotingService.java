package com.zltel.broadcast.eventplan.service;

import java.util.List;

import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItemKey;
import com.zltel.broadcast.eventplan.bean.VotingResult;

public interface EventPlanVotingService {
    int deleteByPrimaryKey(EventPlanVotingItemKey key);

    int insert(EventPlanVotingItem record);

    int insertSelective(EventPlanVotingItem record);

    EventPlanVotingItem selectByPrimaryKey(EventPlanVotingItemKey key);

    int updateByPrimaryKeySelective(EventPlanVotingItem record);

    int updateByPrimaryKey(EventPlanVotingItem record);

    /** 查询 投票信息 **/
    List<EventPlanVotingItem> query(EventPlanVotingItem item);

    /** 删除投票信息 **/
    int delete(EventPlanVotingItem item);

    /** 查询活动投票表决情况 , 如未表决则为空 **/
    List<EventPlanVotingItem> queryVotingInfo(Integer eventPlanId);

    /** 统计活动投票信息, 判断是否通过 **/
    boolean votingPass(Integer eventPlanId);
    /**投票结果**/
    VotingResult votingResult(Integer eventPlanId);
    /**投票**/
    void voting(EventPlanVotingItem record);

}
