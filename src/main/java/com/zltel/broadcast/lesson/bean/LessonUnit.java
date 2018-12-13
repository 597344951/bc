package com.zltel.broadcast.lesson.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * lesson_unit 课程信息
 * 
 * @author
 */
public class LessonUnit implements Serializable {
    /**
     * 课程id
     */
    private Integer lessonUnitId;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 所属类别
     */
    private Integer categoryId;

    private Integer orgId;

    private Integer userId;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 测试资格限定(0：不限制，1：学时限定)
     */
    private Integer testThreshold;

    /**
     * 学分百分比
     */
    private Integer creditHoursPercent;

    /**
     * 学习人员限定(0：不限制，1：限定组织人员）
     */
    private Integer learnerLimit;

    /**
     * 课程描述信息
     */
    private String descript;

    private static final long serialVersionUID = 1L;

    // --------------懒加载属性--------------
    /** 课程考试类型 **/
    private List<LessonUnitTest> testTypes;
    /** 所属分类 **/
    private LessonCategory category;
    /** 允许学习的组织id **/
    private List<LessonLearnerLimit> allowOrgIds;
    /** 课程小节列表 **/
    private List<LessonSection> lessonList;
    /**课程shu6**/
    private List<LessonSection> lessonTree;
    /**报名信息**/
    private List<LessonUnitRegistration> lessonRegistration;
    /**课程学习进度**/
    private List<LessonUnitProgress> lessonProgress;
    

    // --------------查询参数---------------
    /** 搜索关键字 **/
    private String keyword;
    /** 子目录id **/
    private List<Integer> categoryChildrenIds;
    /**当前学习的组织id**/
    private Integer learnOrgId;

    public List<LessonUnitRegistration> getLessonRegistration() {
        return lessonRegistration;
    }


    public void setLessonRegistration(List<LessonUnitRegistration> lessonRegistration) {
        this.lessonRegistration = lessonRegistration;
    }


    public List<LessonUnitProgress> getLessonProgress() {
        return lessonProgress;
    }


    public void setLessonProgress(List<LessonUnitProgress> lessonProgress) {
        this.lessonProgress = lessonProgress;
    }


    public Integer getLessonUnitId() {
        return lessonUnitId;
    }


    public List<LessonSection> getLessonTree() {
        return lessonTree;
    }


    public void setLessonTree(List<LessonSection> lessonTree) {
        this.lessonTree = lessonTree;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getTestThreshold() {
        return testThreshold;
    }

    public void setTestThreshold(Integer testThreshold) {
        this.testThreshold = testThreshold;
    }

    public Integer getCreditHoursPercent() {
        return creditHoursPercent;
    }

    public void setCreditHoursPercent(Integer creditHoursPercent) {
        this.creditHoursPercent = creditHoursPercent;
    }

    public Integer getLearnerLimit() {
        return learnerLimit;
    }

    public void setLearnerLimit(Integer learnerLimit) {
        this.learnerLimit = learnerLimit;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }


    public List<LessonUnitTest> getTestTypes() {
        return testTypes;
    }

    public void setTestTypes(List<LessonUnitTest> testTypes) {
        this.testTypes = testTypes;
    }

    public LessonCategory getCategory() {
        return category;
    }

    public void setCategory(LessonCategory category) {
        this.category = category;
    }

    public List<LessonLearnerLimit> getAllowOrgIds() {
        return allowOrgIds;
    }

    public void setAllowOrgIds(List<LessonLearnerLimit> allowOrgIds) {
        this.allowOrgIds = allowOrgIds;
    }

    public List<LessonSection> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<LessonSection> lessonList) {
        this.lessonList = lessonList;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getCategoryChildrenIds() {
        return categoryChildrenIds;
    }

    public void setCategoryChildrenIds(List<Integer> categoryChildrenIds) {
        this.categoryChildrenIds = categoryChildrenIds;
    }

    public Integer getLearnOrgId() {
        return learnOrgId;
    }

    public void setLearnOrgId(Integer learnOrgId) {
        this.learnOrgId = learnOrgId;
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
        LessonUnit other = (LessonUnit) that;
        return (this.getLessonUnitId() == null
                ? other.getLessonUnitId() == null
                : this.getLessonUnitId().equals(other.getLessonUnitId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCategoryId() == null
                        ? other.getCategoryId() == null
                        : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getAddTime() == null
                        ? other.getAddTime() == null
                        : this.getAddTime().equals(other.getAddTime()))
                && (this.getTestThreshold() == null
                        ? other.getTestThreshold() == null
                        : this.getTestThreshold().equals(other.getTestThreshold()))
                && (this.getCreditHoursPercent() == null
                        ? other.getCreditHoursPercent() == null
                        : this.getCreditHoursPercent().equals(other.getCreditHoursPercent()))
                && (this.getLearnerLimit() == null
                        ? other.getLearnerLimit() == null
                        : this.getLearnerLimit().equals(other.getLearnerLimit()))
                && (this.getDescript() == null
                        ? other.getDescript() == null
                        : this.getDescript().equals(other.getDescript()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLessonUnitId() == null) ? 0 : getLessonUnitId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getTestThreshold() == null) ? 0 : getTestThreshold().hashCode());
        result = prime * result + ((getCreditHoursPercent() == null) ? 0 : getCreditHoursPercent().hashCode());
        result = prime * result + ((getLearnerLimit() == null) ? 0 : getLearnerLimit().hashCode());
        result = prime * result + ((getDescript() == null) ? 0 : getDescript().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lessonUnitId=").append(lessonUnitId);
        sb.append(", name=").append(name);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", orgId=").append(orgId);
        sb.append(", userId=").append(userId);
        sb.append(", addTime=").append(addTime);
        sb.append(", testThreshold=").append(testThreshold);
        sb.append(", creditHoursPercent=").append(creditHoursPercent);
        sb.append(", learnerLimit=").append(learnerLimit);
        sb.append(", descript=").append(descript);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
