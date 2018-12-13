package com.zltel.broadcast.lesson.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * lesson_section
 * @author 
 */
public class LessonSection implements Serializable {
    private Integer lessonId;

    private Integer lessonUnitId;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程资源类型(0：内部资源地址，1：视频外链)
            
     */
    private Integer sourceType;

    /**
     * 课程数据信息
     */
    private String sourceData;

    /**
     * 增加时间
     */
    private Date addTime;

    /**
     * 学时(单位分钟)
     */
    private Integer creditHours;

    /**
     * 课程描述
     */
    private String descript;
    
    private Integer parent;
    private Integer sortNum;
    
    //-----
    private List<LessonSection> children;

    private static final long serialVersionUID = 1L;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public List<LessonSection> getChildren() {
        return children;
    }

    public void setChildren(List<LessonSection> children) {
        this.children = children;
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
        LessonSection other = (LessonSection) that;
        return (this.getLessonId() == null ? other.getLessonId() == null : this.getLessonId().equals(other.getLessonId()))
            && (this.getLessonUnitId() == null ? other.getLessonUnitId() == null : this.getLessonUnitId().equals(other.getLessonUnitId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getSourceData() == null ? other.getSourceData() == null : this.getSourceData().equals(other.getSourceData()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getCreditHours() == null ? other.getCreditHours() == null : this.getCreditHours().equals(other.getCreditHours()))
            && (this.getDescript() == null ? other.getDescript() == null : this.getDescript().equals(other.getDescript()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLessonId() == null) ? 0 : getLessonId().hashCode());
        result = prime * result + ((getLessonUnitId() == null) ? 0 : getLessonUnitId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getSourceData() == null) ? 0 : getSourceData().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getCreditHours() == null) ? 0 : getCreditHours().hashCode());
        result = prime * result + ((getDescript() == null) ? 0 : getDescript().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lessonId=").append(lessonId);
        sb.append(", lessonUnitId=").append(lessonUnitId);
        sb.append(", name=").append(name);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", sourceData=").append(sourceData);
        sb.append(", addTime=").append(addTime);
        sb.append(", creditHours=").append(creditHours);
        sb.append(", descript=").append(descript);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}