package com.zltel.broadcast.lesson.bean;

import java.io.Serializable;

/**
 * lesson_unit_test 课程考试类型
 * @author 
 */
public class LessonUnitTest implements Serializable {
    /**
     * 测试id
     */
    private Integer testId;

    /**
     * 课程id
     */
    private Integer lessonUnitId;

    /**
     * 测试类型(1：提交论文，2：参与在线考试)
     */
    private Integer testType;

    /**
     * 测试名称
     */
    private String name;

    /**
     * 测试描述
     */
    private String descript;

    private static final long serialVersionUID = 1L;

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getLessonUnitId() {
        return lessonUnitId;
    }

    public void setLessonUnitId(Integer lessonUnitId) {
        this.lessonUnitId = lessonUnitId;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
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
        LessonUnitTest other = (LessonUnitTest) that;
        return (this.getTestId() == null ? other.getTestId() == null : this.getTestId().equals(other.getTestId()))
            && (this.getLessonUnitId() == null ? other.getLessonUnitId() == null : this.getLessonUnitId().equals(other.getLessonUnitId()))
            && (this.getTestType() == null ? other.getTestType() == null : this.getTestType().equals(other.getTestType()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescript() == null ? other.getDescript() == null : this.getDescript().equals(other.getDescript()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTestId() == null) ? 0 : getTestId().hashCode());
        result = prime * result + ((getLessonUnitId() == null) ? 0 : getLessonUnitId().hashCode());
        result = prime * result + ((getTestType() == null) ? 0 : getTestType().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescript() == null) ? 0 : getDescript().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", testId=").append(testId);
        sb.append(", lessonUnitId=").append(lessonUnitId);
        sb.append(", testType=").append(testType);
        sb.append(", name=").append(name);
        sb.append(", descript=").append(descript);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}