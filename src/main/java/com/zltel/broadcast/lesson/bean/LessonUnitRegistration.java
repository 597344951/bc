package com.zltel.broadcast.lesson.bean;

import java.util.Date;

public class LessonUnitRegistration {
    private Integer id;
    private Integer lessonUnitId;
    private Integer userId;
    private Integer orgId;
    private Date addTime;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getLessonUnitId() {
        return lessonUnitId;
    }
    public void setLessonUnitId(Integer lessonUnitId) {
        this.lessonUnitId = lessonUnitId;
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
    public Date getAddTime() {
        return addTime;
    }
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    
     
    
}