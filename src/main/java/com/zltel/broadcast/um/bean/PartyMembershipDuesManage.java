package com.zltel.broadcast.um.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PartyMembershipDuesManage {
    private Integer id;

    private Integer userId;

    private Integer orgId;

    private BigDecimal shouldPayMoney;

    private Date shouldPayDateStart;

    private Date shouldPayDateEnd;

    private String shouldPayDescribe;

    private BigDecimal paidMoney;

    private Date paidDate;

    private String paidDescribe;

    private Integer paidWay;

    private Integer payStatus;

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

    public BigDecimal getShouldPayMoney() {
        return shouldPayMoney;
    }

    public void setShouldPayMoney(BigDecimal shouldPayMoney) {
        this.shouldPayMoney = shouldPayMoney;
    }

    public Date getShouldPayDateStart() {
        return shouldPayDateStart;
    }

    public void setShouldPayDateStart(Date shouldPayDateStart) {
        this.shouldPayDateStart = shouldPayDateStart;
    }

    public Date getShouldPayDateEnd() {
        return shouldPayDateEnd;
    }

    public void setShouldPayDateEnd(Date shouldPayDateEnd) {
        this.shouldPayDateEnd = shouldPayDateEnd;
    }

    public String getShouldPayDescribe() {
        return shouldPayDescribe;
    }

    public void setShouldPayDescribe(String shouldPayDescribe) {
        this.shouldPayDescribe = shouldPayDescribe == null ? null : shouldPayDescribe.trim();
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidDescribe() {
        return paidDescribe;
    }

    public void setPaidDescribe(String paidDescribe) {
        this.paidDescribe = paidDescribe == null ? null : paidDescribe.trim();
    }

    public Integer getPaidWay() {
        return paidWay;
    }

    public void setPaidWay(Integer paidWay) {
        this.paidWay = paidWay;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}