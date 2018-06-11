package com.zltel.broadcast.um.bean;

import java.util.Date;

public class PartyUserInfo {
    private Integer partyUserId;

    private Integer type;

    private Date joinDateFormal;

    private Date joinDateReserve;

    private Integer status;

    private String workUnit;

    private Integer workNature;

    private Date joinWorkDate;

    private Integer appointmentTimeLength;

    private Integer joinPartyBranchTypeId;

    private Integer firstLineSituation;

    private Integer partyStaff;

    private Integer partyRepresentative;

    private Integer volunteer;

    private Integer difficultUser;

    private Integer advancedUser;

    private Integer positiveUser;

    private Integer developUser;

    private Integer missingUser;

    private String introduce;

    public Integer getPartyUserId() {
        return partyUserId;
    }

    public void setPartyUserId(Integer partyUserId) {
        this.partyUserId = partyUserId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getJoinDateFormal() {
        return joinDateFormal;
    }

    public void setJoinDateFormal(Date joinDateFormal) {
        this.joinDateFormal = joinDateFormal;
    }

    public Date getJoinDateReserve() {
        return joinDateReserve;
    }

    public void setJoinDateReserve(Date joinDateReserve) {
        this.joinDateReserve = joinDateReserve;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public Integer getWorkNature() {
        return workNature;
    }

    public void setWorkNature(Integer workNature) {
        this.workNature = workNature;
    }

    public Date getJoinWorkDate() {
        return joinWorkDate;
    }

    public void setJoinWorkDate(Date joinWorkDate) {
        this.joinWorkDate = joinWorkDate;
    }

    public Integer getAppointmentTimeLength() {
        return appointmentTimeLength;
    }

    public void setAppointmentTimeLength(Integer appointmentTimeLength) {
        this.appointmentTimeLength = appointmentTimeLength;
    }

    public Integer getJoinPartyBranchTypeId() {
        return joinPartyBranchTypeId;
    }

    public void setJoinPartyBranchTypeId(Integer joinPartyBranchTypeId) {
        this.joinPartyBranchTypeId = joinPartyBranchTypeId;
    }

    public Integer getFirstLineSituation() {
        return firstLineSituation;
    }

    public void setFirstLineSituation(Integer firstLineSituation) {
        this.firstLineSituation = firstLineSituation;
    }

    public Integer getPartyStaff() {
        return partyStaff;
    }

    public void setPartyStaff(Integer partyStaff) {
        this.partyStaff = partyStaff;
    }

    public Integer getPartyRepresentative() {
        return partyRepresentative;
    }

    public void setPartyRepresentative(Integer partyRepresentative) {
        this.partyRepresentative = partyRepresentative;
    }

    public Integer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Integer volunteer) {
        this.volunteer = volunteer;
    }

    public Integer getDifficultUser() {
        return difficultUser;
    }

    public void setDifficultUser(Integer difficultUser) {
        this.difficultUser = difficultUser;
    }

    public Integer getAdvancedUser() {
        return advancedUser;
    }

    public void setAdvancedUser(Integer advancedUser) {
        this.advancedUser = advancedUser;
    }

    public Integer getPositiveUser() {
        return positiveUser;
    }

    public void setPositiveUser(Integer positiveUser) {
        this.positiveUser = positiveUser;
    }

    public Integer getDevelopUser() {
        return developUser;
    }

    public void setDevelopUser(Integer developUser) {
        this.developUser = developUser;
    }

    public Integer getMissingUser() {
        return missingUser;
    }

    public void setMissingUser(Integer missingUser) {
        this.missingUser = missingUser;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }
}