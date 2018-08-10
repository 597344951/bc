package com.zltel.broadcast.applicationform.bean;

public class ApplicationUserData {
    private Integer id;

    private Integer userFormId;

    private Integer fieldId;

    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserFormId() {
        return userFormId;
    }

    public void setUserFormId(Integer userFormId) {
        this.userFormId = userFormId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}