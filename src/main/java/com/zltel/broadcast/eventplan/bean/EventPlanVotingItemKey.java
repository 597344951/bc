package com.zltel.broadcast.eventplan.bean;

import javax.validation.constraints.NotNull;

public class EventPlanVotingItemKey {
    
    @NotNull(message="投票活动不能为空")
    private Integer eventPlanId;
    @NotNull(message="投票投票用户不能为空")
    private Integer userId;

    public Integer getEventPlanId() {
        return eventPlanId;
    }

    public void setEventPlanId(Integer eventPlanId) {
        this.eventPlanId = eventPlanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}