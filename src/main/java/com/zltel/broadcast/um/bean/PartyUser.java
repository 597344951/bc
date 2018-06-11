package com.zltel.broadcast.um.bean;

import java.util.Date;

public class PartyUser extends BaseUser {
    private Integer uid;

    private Date joinDate;

    private Integer partyStaff;

    private Integer partRepresentative;

    private Integer volunteer;

    private Integer difficultMember;

    private Integer advancedMember;

    private Integer reserveMember;

    private Date reserveApprovalDate;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getPartyStaff() {
        return partyStaff;
    }

    public void setPartyStaff(Integer partyStaff) {
        this.partyStaff = partyStaff;
    }

    public Integer getPartRepresentative() {
        return partRepresentative;
    }

    public void setPartRepresentative(Integer partRepresentative) {
        this.partRepresentative = partRepresentative;
    }

    public Integer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Integer volunteer) {
        this.volunteer = volunteer;
    }

    public Integer getDifficultMember() {
        return difficultMember;
    }

    public void setDifficultMember(Integer difficultMember) {
        this.difficultMember = difficultMember;
    }

    public Integer getAdvancedMember() {
        return advancedMember;
    }

    public void setAdvancedMember(Integer advancedMember) {
        this.advancedMember = advancedMember;
    }

    public Integer getReserveMember() {
        return reserveMember;
    }

    public void setReserveMember(Integer reserveMember) {
        this.reserveMember = reserveMember;
    }

    public Date getReserveApprovalDate() {
        return reserveApprovalDate;
    }

    public void setReserveApprovalDate(Date reserveApprovalDate) {
        this.reserveApprovalDate = reserveApprovalDate;
    }
}