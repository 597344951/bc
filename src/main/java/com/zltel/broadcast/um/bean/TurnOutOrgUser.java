package com.zltel.broadcast.um.bean;

import java.util.Date;

public class TurnOutOrgUser {
    private Integer id;

    private Integer userId;

    private Integer turnOutOrgId;
    
    private String otherOrgName;

    private String joinStatus;

    private Date time;

    private Integer nowStep;

    private Boolean isHistory;

    public String getOtherOrgName() {
		return otherOrgName;
	}

	public void setOtherOrgName(String otherOrgName) {
		this.otherOrgName = otherOrgName;
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

    public Integer getTurnOutOrgId() {
        return turnOutOrgId;
    }

    public void setTurnOutOrgId(Integer turnOutOrgId) {
        this.turnOutOrgId = turnOutOrgId;
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

    public Boolean getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Boolean isHistory) {
        this.isHistory = isHistory;
    }
}