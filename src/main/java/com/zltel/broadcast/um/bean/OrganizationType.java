package com.zltel.broadcast.um.bean;

public class OrganizationType {
    private Integer orgTypeId;

    private String orgTypeName;
    
    private boolean isPartyOrg;
    
    private boolean orgIsBranch;
    
    private boolean orgSetJoin;
    
    private String noOrgTypeName;

    private String orgTypeDescribe;

    

	public boolean isPartyOrg() {
		return isPartyOrg;
	}

	public void setPartyOrg(boolean isPartyOrg) {
		this.isPartyOrg = isPartyOrg;
	}

	public boolean isOrgIsBranch() {
		return orgIsBranch;
	}

	public void setOrgIsBranch(boolean orgIsBranch) {
		this.orgIsBranch = orgIsBranch;
	}

	public boolean isOrgSetJoin() {
		return orgSetJoin;
	}

	public void setOrgSetJoin(boolean orgSetJoin) {
		this.orgSetJoin = orgSetJoin;
	}

	public String getNoOrgTypeName() {
		return noOrgTypeName;
	}

	public void setNoOrgTypeName(String noOrgTypeName) {
		this.noOrgTypeName = noOrgTypeName;
	}

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