package com.zltel.broadcast.um.bean;

import java.util.Date;

public class JoinPartyOrgUser {
    private Integer id;

    private Integer userId;
    
    private Integer joinOrgId;

    private Integer joinPartyType;

    private String joinStatus;

    private Date time;

    private Integer nowStep;

    private Integer isHistory;

    public Integer getJoinOrgId() {
		return joinOrgId;
	}

	public void setJoinOrgId(Integer joinOrgId) {
		this.joinOrgId = joinOrgId;
	}

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

    public Integer getJoinPartyType() {
        return joinPartyType;
    }

    public void setJoinPartyType(Integer joinPartyType) {
        this.joinPartyType = joinPartyType;
    }

    public String getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus == null ? null : joinStatus.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getNowStep() {
        return nowStep;
    }

    public void setNowStep(Integer nowStep) {
        this.nowStep = nowStep;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }
}