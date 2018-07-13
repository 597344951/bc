package com.zltel.broadcast.um.bean;

import java.math.BigDecimal;

public class IntegralConstitute {
    private Integer icId;
    
    private Integer orgId;

    private String type;

    private BigDecimal integral;

    private String describes;

    private Integer parentIcId;

    public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getIcId() {
        return icId;
    }

    public void setIcId(Integer icId) {
        this.icId = icId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }

    public Integer getParentIcId() {
        return parentIcId;
    }

    public void setParentIcId(Integer parentIcId) {
        this.parentIcId = parentIcId;
    }
}