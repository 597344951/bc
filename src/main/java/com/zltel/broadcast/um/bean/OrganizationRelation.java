package com.zltel.broadcast.um.bean;

import java.util.Date;

public class OrganizationRelation {
    private Integer orgRltId;

    private Integer orgRltInfoId;

    private Integer orgRltDutyId;

    private Integer orgRltUserId;
    
    private Date orgRltJoinTime;
    
    private boolean thisOrgFlow;

	public boolean isThisOrgFlow() {
		return thisOrgFlow;
	}

	public void setThisOrgFlow(boolean thisOrgFlow) {
		this.thisOrgFlow = thisOrgFlow;
	}

	public Date getOrgRltJoinTime() {
		return orgRltJoinTime;
	}

	public void setOrgRltJoinTime(Date orgRltJoinTime) {
		this.orgRltJoinTime = orgRltJoinTime;
	}

	public Integer getOrgRltId() {
        return orgRltId;
    }

    public void setOrgRltId(Integer orgRltId) {
        this.orgRltId = orgRltId;
    }

    public Integer getOrgRltInfoId() {
        return orgRltInfoId;
    }

    public void setOrgRltInfoId(Integer orgRltInfoId) {
        this.orgRltInfoId = orgRltInfoId;
    }

    public Integer getOrgRltDutyId() {
        return orgRltDutyId;
    }

    public void setOrgRltDutyId(Integer orgRltDutyId) {
        this.orgRltDutyId = orgRltDutyId;
    }

    public Integer getOrgRltUserId() {
        return orgRltUserId;
    }

    public void setOrgRltUserId(Integer orgRltUserId) {
        this.orgRltUserId = orgRltUserId;
    }
}