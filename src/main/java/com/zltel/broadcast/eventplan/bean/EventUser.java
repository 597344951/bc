package com.zltel.broadcast.eventplan.bean;

import javax.validation.constraints.NotNull;

public class EventUser extends EventSystem{
    private Integer orgId;

    //临时事件  或 周期性事件
    @NotNull(message="必须指定事件类型")
    private String event_type;
    
     
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取event_type  
     * @return the event_type
     */
    public String getEvent_type() {
        return event_type;
    }

    /**
     * 设置event_type  
     * @param event_type the event_type to set
     */
    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }
    
}