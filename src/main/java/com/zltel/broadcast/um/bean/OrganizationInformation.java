package com.zltel.broadcast.um.bean;

public class OrganizationInformation {
    private Integer orgInfoId;

    private String orgInfoName;

    private String orgInfoCommitteeProvince;

    private String orgInfoCommitteeCity;

    private String orgInfoCommitteeArea;

    private String orgInfoCommitteeDetail;
    
    @SuppressWarnings("unused")
	private String orgInfoCommitteeAddress;

    public String getOrgInfoCommitteeAddress() {
		return orgInfoCommitteeProvince + "-" + orgInfoCommitteeCity + "-" + orgInfoCommitteeArea
				+ "-" + orgInfoCommitteeDetail;
	}

	public void setOrgInfoCommitteeAddress(String orgInfoCommitteeAddress) {
		this.orgInfoCommitteeAddress = orgInfoCommitteeProvince + "-" + orgInfoCommitteeCity + "-" + orgInfoCommitteeArea
				+ "-" + orgInfoCommitteeDetail;
	}

	private String orgInfoManageProvince;

    private String orgInfoManageCity;

    private String orgInfoManageArea;

    private String orgInfoManageDetail;

    private Integer orgInfoParentId;
    
    private Integer noOrgInfoParentId;

    private Integer orgInfoTypeId;

    private String orgInfoDescribe;
    
    private Integer orgInfoNatureId;
    
    private Integer orgInfoLevel;

    public Integer getNoOrgInfoParentId() {
		return noOrgInfoParentId;
	}

	public void setNoOrgInfoParentId(Integer noOrgInfoParentId) {
		this.noOrgInfoParentId = noOrgInfoParentId;
	}

	public Integer getOrgInfoLevel() {
		return orgInfoLevel;
	}

	public void setOrgInfoLevel(Integer orgInfoLevel) {
		this.orgInfoLevel = orgInfoLevel;
	}

	public Integer getOrgInfoNatureId() {
		return orgInfoNatureId;
	}

	public void setOrgInfoNatureId(Integer orgInfoNatureId) {
		this.orgInfoNatureId = orgInfoNatureId;
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

    public String getOrgInfoCommitteeProvince() {
        return orgInfoCommitteeProvince;
    }

    public void setOrgInfoCommitteeProvince(String orgInfoCommitteeProvince) {
        this.orgInfoCommitteeProvince = orgInfoCommitteeProvince == null ? null : orgInfoCommitteeProvince.trim();
    }

    public String getOrgInfoCommitteeCity() {
        return orgInfoCommitteeCity;
    }

    public void setOrgInfoCommitteeCity(String orgInfoCommitteeCity) {
        this.orgInfoCommitteeCity = orgInfoCommitteeCity == null ? null : orgInfoCommitteeCity.trim();
    }

    public String getOrgInfoCommitteeArea() {
        return orgInfoCommitteeArea;
    }

    public void setOrgInfoCommitteeArea(String orgInfoCommitteeArea) {
        this.orgInfoCommitteeArea = orgInfoCommitteeArea == null ? null : orgInfoCommitteeArea.trim();
    }

    public String getOrgInfoCommitteeDetail() {
        return orgInfoCommitteeDetail;
    }

    public void setOrgInfoCommitteeDetail(String orgInfoCommitteeDetail) {
        this.orgInfoCommitteeDetail = orgInfoCommitteeDetail == null ? null : orgInfoCommitteeDetail.trim();
    }

    public String getOrgInfoManageProvince() {
        return orgInfoManageProvince;
    }

    public void setOrgInfoManageProvince(String orgInfoManageProvince) {
        this.orgInfoManageProvince = orgInfoManageProvince == null ? null : orgInfoManageProvince.trim();
    }

    public String getOrgInfoManageCity() {
        return orgInfoManageCity;
    }

    public void setOrgInfoManageCity(String orgInfoManageCity) {
        this.orgInfoManageCity = orgInfoManageCity == null ? null : orgInfoManageCity.trim();
    }

    public String getOrgInfoManageArea() {
        return orgInfoManageArea;
    }

    public void setOrgInfoManageArea(String orgInfoManageArea) {
        this.orgInfoManageArea = orgInfoManageArea == null ? null : orgInfoManageArea.trim();
    }

    public String getOrgInfoManageDetail() {
        return orgInfoManageDetail;
    }

    public void setOrgInfoManageDetail(String orgInfoManageDetail) {
        this.orgInfoManageDetail = orgInfoManageDetail == null ? null : orgInfoManageDetail.trim();
    }

    public Integer getOrgInfoParentId() {
        return orgInfoParentId;
    }

    public void setOrgInfoParentId(Integer orgInfoParentId) {
        this.orgInfoParentId = orgInfoParentId;
    }

    public Integer getOrgInfoTypeId() {
        return orgInfoTypeId;
    }

    public void setOrgInfoTypeId(Integer orgInfoTypeId) {
        this.orgInfoTypeId = orgInfoTypeId;
    }

    public String getOrgInfoDescribe() {
        return orgInfoDescribe;
    }

    public void setOrgInfoDescribe(String orgInfoDescribe) {
        this.orgInfoDescribe = orgInfoDescribe == null ? null : orgInfoDescribe.trim();
    }
}