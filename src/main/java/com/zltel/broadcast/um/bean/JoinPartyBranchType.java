package com.zltel.broadcast.um.bean;

public class JoinPartyBranchType {
    private Integer jpbtId;

    private String joinType;

    private Integer describes;

    public Integer getJpbtId() {
        return jpbtId;
    }

    public void setJpbtId(Integer jpbtId) {
        this.jpbtId = jpbtId;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    public Integer getdescribes() {
        return describes;
    }

    public void setdescribes(Integer describes) {
        this.describes = describes;
    }
}