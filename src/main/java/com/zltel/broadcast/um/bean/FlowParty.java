package com.zltel.broadcast.um.bean;

public class FlowParty {
    private Integer id;

    private Integer userId;

    private Integer orgId;

    private Integer flowTime;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Integer flowTime) {
        this.flowTime = flowTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}