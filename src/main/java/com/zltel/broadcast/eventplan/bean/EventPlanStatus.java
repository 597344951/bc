package com.zltel.broadcast.eventplan.bean;

public class EventPlanStatus {
    public static final Integer STATUS_NOT_PAST = -1;
    public static final Integer STATUS_READY = 0;
    public static final Integer STATUS_VOTING = 1;
    public static final Integer STATUS_PROGRESS = 2;
    public static final Integer STATUS_END = 3;
    
    private Integer status;

    private String name;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}