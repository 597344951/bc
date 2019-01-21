package com.zltel.broadcast.meeting.room.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MeetingRoomOrder {
    private Integer id;

    private Date updateDate;

    private Date addDate;

    private Integer meetingId;

    private String meetingName;

    private Integer meetingRoomId;

    private Date startDate;

    private Date endDate;

    private Integer state;

    public MeetingRoomOrder() {}

    public MeetingRoomOrder(Integer meetingId, String meetingName, Integer meetingRoomId, Date startDate, Date endDate) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingRoomId = meetingRoomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = 0;
        this.addDate = new Date(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public Integer getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(Integer meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd日   aa hh:mm", Locale.CHINESE);
        return simpleDateFormat.format(this.startDate) + " - " + simpleDateFormat.format(this.endDate);
    }
}