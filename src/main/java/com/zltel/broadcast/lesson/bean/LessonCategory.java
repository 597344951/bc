package com.zltel.broadcast.lesson.bean;

import java.io.Serializable;
import java.util.List;

/**
 * lesson_category
 * 
 * @author
 */
public class LessonCategory implements Serializable {
    private Integer categoryId;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 所属组织
     */
    private Integer orgId;

    /**
     * 创建用户id
     */
    private Integer userId;

    /**
     * 排序序号
     */
    private Integer orderNum;

    /**
     * 上一级目录
     */
    private Integer parent;
    /** 子节点 **/
    private List<LessonCategory> children;

    private static final long serialVersionUID = 1L;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public List<LessonCategory> getChildren() {
        return children;
    }

    public void setChildren(List<LessonCategory> children) {
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
        LessonCategory other = (LessonCategory) that;
        return (this.getCategoryId() == null
                ? other.getCategoryId() == null
                : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getOrderNum() == null
                        ? other.getOrderNum() == null
                        : this.getOrderNum().equals(other.getOrderNum()))
                && (this.getParent() == null ? other.getParent() == null : this.getParent().equals(other.getParent()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getParent() == null) ? 0 : getParent().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categoryId=").append(categoryId);
        sb.append(", name=").append(name);
        sb.append(", orgId=").append(orgId);
        sb.append(", userId=").append(userId);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", parent=").append(parent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
