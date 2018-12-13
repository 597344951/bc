package com.zltel.broadcast.um.bean;

public class FlowParty {
    private Integer id;

    private Integer userId;

    private Integer orgId;

    private Integer flowTime;

    private String reason;
    
    private String flowAddressProvince;

    private String flowAddressCity;

    private String flowAddressArea;

    private String flowAddressDetail;


	public String getFlowAddressProvince() {
		return flowAddressProvince;
	}

	public void setFlowAddressProvince(String flowAddressProvince) {
		this.flowAddressProvince = flowAddressProvince;
	}

	public String getFlowAddressCity() {
		return flowAddressCity;
	}

	public void setFlowAddressCity(String flowAddressCity) {
		this.flowAddressCity = flowAddressCity;
	}

	public String getFlowAddressArea() {
		return flowAddressArea;
	}

	public void setFlowAddressArea(String flowAddressArea) {
		this.flowAddressArea = flowAddressArea;
	}

	public String getFlowAddressDetail() {
		return flowAddressDetail;
	}

	public void setFlowAddressDetail(String flowAddressDetail) {
		this.flowAddressDetail = flowAddressDetail;
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Integer flowTime) {
        this.flowTime = flowTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}