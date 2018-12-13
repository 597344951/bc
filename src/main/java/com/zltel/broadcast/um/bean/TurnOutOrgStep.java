package com.zltel.broadcast.um.bean;

import java.util.Date;

public class TurnOutOrgStep {
    private Integer id;

    private Integer turnOutId;

    private Integer processId;

    private String stepTitle;

    private String stepDescribes;

    private String stepStatus;

    private String stepStatusReason;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTurnOutId() {
        return turnOutId;
    }

    public void setTurnOutId(Integer turnOutId) {
        this.turnOutId = turnOutId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getStepTitle() {
        return stepTitle;
    }

    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle == null ? null : stepTitle.trim();
    }

    public String getStepDescribes() {
        return stepDescribes;
    }

    public void setStepDescribes(String stepDescribes) {
        this.stepDescribes = stepDescribes == null ? null : stepDescribes.trim();
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus == null ? null : stepStatus.trim();
    }

    public String getStepStatusReason() {
        return stepStatusReason;
    }

    public void setStepStatusReason(String stepStatusReason) {
        this.stepStatusReason = stepStatusReason == null ? null : stepStatusReason.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}