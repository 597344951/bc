package com.zltel.broadcast.um.bean;

import java.util.Date;

public class JoinPartyOrgStep {
    private Integer id;

    private Integer joinId;

    private Integer processId;

    private String stepTitle;

    private String stepDescribes;

    private String stepStatus;
    
    private String stepStatusReason;

    private Date time;

    public String getStepStatusReason() {
		return stepStatusReason;
	}

	public void setStepStatusReason(String stepStatusReason) {
		this.stepStatusReason = stepStatusReason;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}