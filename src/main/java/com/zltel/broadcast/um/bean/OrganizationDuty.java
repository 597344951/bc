package com.zltel.broadcast.um.bean;

public class OrganizationDuty {
    private Integer orgDutyId;

    private String orgDutyName;

    private String orgDutyDescribe;

    private String orgDutyTypeId;
    
    private Integer orgDutyOrgInfoId;

    private Integer orgDutyParentId;

    public Integer getOrgDutyOrgInfoId() {
		return orgDutyOrgInfoId;
	}

	public void setOrgDutyOrgInfoId(Integer orgDutyOrgInfoId) {
		this.orgDutyOrgInfoId = orgDutyOrgInfoId;
	}

	public Integer getOrgDutyId() {
        return orgDutyId;
    }

    public void setOrgDutyId(Integer orgDutyId) {
        this.orgDutyId = orgDutyId;
    }

    public String getOrgDutyName() {
        return orgDutyName;
    }

    public void setOrgDutyName(String orgDutyName) {
        this.orgDutyName = orgDutyName == null ? null : orgDutyName.trim();
    }

    public String getOrgDutyDescribe() {
        return orgDutyDescribe;
    }

    public void setOrgDutyDescribe(String orgDutyDescribe) {
        this.orgDutyDescribe = orgDutyDescribe == null ? null : orgDutyDescribe.trim();
    }

    public String getOrgDutyTypeId() {
        return orgDutyTypeId;
    }

    public void setOrgDutyTypeId(String orgDutyTypeId) {
        this.orgDutyTypeId = orgDutyTypeId == null ? null : orgDutyTypeId.trim();
    }

    public Integer getOrgDutyParentId() {
        return orgDutyParentId;
    }

    public void setOrgDutyParentId(Integer orgDutyParentId) {
        this.orgDutyParentId = orgDutyParentId;
    }
}