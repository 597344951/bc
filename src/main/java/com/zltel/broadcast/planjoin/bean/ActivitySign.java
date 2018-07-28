package com.zltel.broadcast.planjoin.bean;

import java.util.Date;

public class ActivitySign {
    private Integer id;

    private Integer eventPlanId;

    private Integer userId;

    private Date time;

    
    
    public ActivitySign() {
        super();
    }

    public ActivitySign(Integer eventPlanId, Integer userId) {
        super();
        this.eventPlanId = eventPlanId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}