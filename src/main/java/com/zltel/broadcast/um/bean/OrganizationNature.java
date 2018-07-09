package com.zltel.broadcast.um.bean;

public class OrganizationNature {
    private Integer orgNatureId;

    private String orgNatureName;

    private String orgNatureDescribe;

    public Integer getOrgNatureId() {
        return orgNatureId;
    }

    public void setOrgNatureId(Integer orgNatureId) {
        this.orgNatureId = orgNatureId;
    }

    public String getOrgNatureName() {
        return orgNatureName;
    }

    public void setOrgNatureName(String orgNatureName) {
        this.orgNatureName = orgNatureName == null ? null : orgNatureName.trim();
    }

    public String getOrgNatureDescribe() {
        return orgNatureDescribe;
    }

    public void setOrgNatureDescribe(String orgNatureDescribe) {
        this.orgNatureDescribe = orgNatureDescribe == null ? null : orgNatureDescribe.trim();
    }
}