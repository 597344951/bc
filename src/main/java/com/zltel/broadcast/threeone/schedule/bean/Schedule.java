package com.zltel.broadcast.threeone.schedule.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Schedule {
    /**
     * 日程状态
     **/
    public final static int STATE_READY = 0;
    public final static int STATE_REPORTED = 1;
    public final static int STATE_STARTED = 2;
    public final static int STATE_FINISH = 3;
    private Integer id;

    private String name;

    private String description;

    private Integer type;

    private String place;

    private Date startTime;

    private Date endTime;

    private Integer state;

    private Integer userId;

    private Integer orgId;

    private Date addDate;

    private Date updateDate;

    public Schedule() {
    }

    public Schedule(Integer id, Integer state) {
        this.id = id;
        this.state = state;
    }

    private List<Map<String, Object>> members;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Date getAddDate() {
        return addDate == null ? new Date(System.currentTimeMillis()) : addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<Map<String, Object>> getMembers() {
        return members;
    }

    public void setMembers(List<Map<String, Object>> members) {
        this.members = members;
    }

    public String getTypeName() {
        switch (this.type) {
            case 1:
                return "党员小组会";
            case 2:
                return "支部党员大会";
            case 3:
                return "支部委员会";
            case 4:
                return "党课";
            default:
                return "未知类型";
        }
    }

    public String getStartTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd日   aa hh:mm", Locale.CHINESE);
        return simpleDateFormat.format(this.startTime);
    }

    public String getEndTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("aa hh:mm", Locale.CHINESE);
        return simpleDateFormat.format(this.endTime);
    }
}