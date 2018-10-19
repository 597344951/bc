package com.zltel.broadcast.poster.bean;

public class PosterCategoryRelationKey {
    private Integer templateId;

    private Integer categoryId;

    public PosterCategoryRelationKey(Integer templateId, Integer uc) {
        this.templateId = templateId;
        this.categoryId = uc;
    }

    public PosterCategoryRelationKey() {}

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}