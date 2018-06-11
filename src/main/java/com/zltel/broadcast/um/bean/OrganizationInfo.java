package com.zltel.broadcast.um.bean;

public class OrganizationInfo extends OrganizationType {
    private Integer orgInfoId;

    private String orgInfoName;

    private String orgInfoManagerRegion;

    private String orgInfoActiveRegion;

    private String orgInfoDescribe;

    private Integer orgInfoParentId;
    
    private Integer orgInfoTypeId;

    public Integer getOrgInfoTypeId() {
		return orgInfoTypeId;
	}

	public void setOrgInfoTypeId(Integer orgInfoTypeId) {
		this.orgInfoTypeId = orgInfoTypeId;
	}

	public Integer getOrgInfoId() {
        return orgInfoId;
    }

    public void setOrgInfoId(Integer orgInfoId) {
        this.orgInfoId = orgInfoId;
    }

    public String getOrgInfoName() {
        return orgInfoName;
    }

    public void setOrgInfoName(String orgInfoName) {
        this.orgInfoName = orgInfoName == null ? null : orgInfoName.trim();
    }

    public String getOrgInfoManagerRegion() {
        return orgInfoManagerRegion;
    }

    public void setOrgInfoManagerRegion(String orgInfoManagerRegion) {
        this.orgInfoManagerRegion = orgInfoManagerRegion == null ? null : orgInfoManagerRegion.trim();
    }

    public String getOrgInfoActiveRegion() {
        return orgInfoActiveRegion;
    }

    public void setOrgInfoActiveRegion(String orgInfoActiveRegion) {
        this.orgInfoActiveRegion = orgInfoActiveRegion == null ? null : orgInfoActiveRegion.trim();
    }

    public String getOrgInfoDescribe() {
        return orgInfoDescribe;
    }

    public void setOrgInfoDescribe(String orgInfoDescribe) {
        this.orgInfoDescribe = orgInfoDescribe == null ? null : orgInfoDescribe.trim();
    }

    public Integer getOrgInfoParentId() {
        return orgInfoParentId;
    }

    public void setOrgInfoParentId(Integer orgInfoParentId) {
        this.orgInfoParentId = orgInfoParentId;
    }
}