package com.zltel.broadcast.um.bean;

public class OrganizationType {
    private Integer orgTypeId;

    private String orgTypeName;

    private String orgTypeDescribe;

    public Integer getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(Integer orgTypeId) {
        this.orgTypeId = orgTypeId;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName == null ? null : orgTypeName.trim();
    }

    public String getOrgTypeDescribe() {
        return orgTypeDescribe;
    }

    public void setOrgTypeDescribe(String orgTypeDescribe) {
        this.orgTypeDescribe = orgTypeDescribe == null ? null : orgTypeDescribe.trim();
    }
}