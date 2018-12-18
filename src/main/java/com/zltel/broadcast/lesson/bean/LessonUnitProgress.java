package com.zltel.broadcast.lesson.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * lesson_unit_progress 课程学习进度
 * 
 * @author
 */
public class LessonUnitProgress implements Serializable {
    private Integer pid;

    private Integer lessonId;

    private Integer lessonUnitId;

    private Integer userId;

    private Integer orgId;

    /**
     * 获得学时
     */
    private Integer creditHours;

    private Date saveTime;
    
    private Integer playProgress;

    private static final long serialVersionUID = 1L;
    
    private Boolean endToSave;

    public Integer getPlayProgress() {
        return playProgress;
    }

    public void setPlayProgress(Integer playProgress) {
        this.playProgress = playProgress;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
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

    public Integer getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }

    public Boolean getEndToSave() {
        return endToSave;
    }

    public void setEndToSave(Boolean endToSave) {
        this.endToSave = endToSave;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LessonUnitProgress other = (LessonUnitProgress) that;
        return (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
                && (this.getLessonId() == null
                        ? other.getLessonId() == null
                        : this.getLessonId().equals(other.getLessonId()))
                && (this.getLessonUnitId() == null
                        ? other.getLessonUnitId() == null
                        : this.getLessonUnitId().equals(other.getLessonUnitId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
                && (this.getCreditHours() == null
                        ? other.getCreditHours() == null
                        : this.getCreditHours().equals(other.getCreditHours()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getLessonId() == null) ? 0 : getLessonId().hashCode());
        result = prime * result + ((getLessonUnitId() == null) ? 0 : getLessonUnitId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getCreditHours() == null) ? 0 : getCreditHours().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pid=").append(pid);
        sb.append(", lessonId=").append(lessonId);
        sb.append(", lessonUnitId=").append(lessonUnitId);
        sb.append(", userId=").append(userId);
        sb.append(", orgId=").append(orgId);
        sb.append(", creditHours=").append(creditHours);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
