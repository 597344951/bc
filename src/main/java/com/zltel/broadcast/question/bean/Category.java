package com.zltel.broadcast.question.bean;

import java.util.Date;

public class Category {
    private Integer categoryId;

    private String name;

    private String description;

    private Date addDate;

    private Date updateDate;

    public Category() {
    }

    public Category(int id) {
        this.categoryId = id;
}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.addDate = this.updateDate = new Date();
    }

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
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getAddDate() {
        return addDate;
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
}