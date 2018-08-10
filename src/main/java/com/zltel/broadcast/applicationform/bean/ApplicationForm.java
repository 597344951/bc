package com.zltel.broadcast.applicationform.bean;

import java.util.List;

public class ApplicationForm {
    private Integer formId;

    private String title;

    private String remark;

    private Integer orgId;

    private Integer userId;
    
    private List<ApplicationFormFields> fields;

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public List<ApplicationFormFields> getFields() {
        return fields;
    }

    public void setFields(List<ApplicationFormFields> fields) {
        this.fields = fields;
    }
}