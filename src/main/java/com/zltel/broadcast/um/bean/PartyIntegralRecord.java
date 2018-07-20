package com.zltel.broadcast.um.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PartyIntegralRecord {
    private Integer pirId;

    private Integer orgId;

    private Integer partyId;

    private Integer activityId;

    private Integer changeTypeId;

    private String changeDescribes;

    private Integer changeIntegralType;

    private Date changeTime;
    
    private Integer changeScore;

    private BigDecimal weightCoefficient;

    private Integer weightType;

    private Integer isMerge;

    public Integer getChangeScore() {
		return changeScore;
	}

	public void setChangeScore(Integer changeScore) {
		this.changeScore = changeScore;
	}

	public Integer getPirId() {
        return pirId;
    }

    public void setPirId(Integer pirId) {
        this.pirId = pirId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getChangeTypeId() {
        return changeTypeId;
    }

    public void setChangeTypeId(Integer changeTypeId) {
        this.changeTypeId = changeTypeId;
    }

    public String getChangeDescribes() {
        return changeDescribes;
    }

    public void setChangeDescribes(String changeDescribes) {
        this.changeDescribes = changeDescribes == null ? null : changeDescribes.trim();
    }

    public Integer getChangeIntegralType() {
        return changeIntegralType;
    }

    public void setChangeIntegralType(Integer changeIntegralType) {
        this.changeIntegralType = changeIntegralType;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public BigDecimal getWeightCoefficient() {
        return weightCoefficient;
    }

    public void setWeightCoefficient(BigDecimal weightCoefficient) {
        this.weightCoefficient = weightCoefficient;
    }

    public Integer getWeightType() {
        return weightType;
    }

    public void setWeightType(Integer weightType) {
        this.weightType = weightType;
    }

    public Integer getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(Integer isMerge) {
        this.isMerge = isMerge;
    }
}