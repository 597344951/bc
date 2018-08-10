package com.zltel.broadcast.applicationform.bean;

import java.util.List;

public class ApplicationFormFields {
    private Integer fieldId;

    private Integer formId;

    private String label;

    private String name;

    private String type;

    private String remark;

    private Integer sortNum;
    private Integer rowIndex;
    private Integer colWidth;

    private List<ApplicationFormFieldsValues> fieldValues;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public List<ApplicationFormFieldsValues> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<ApplicationFormFieldsValues> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColWidth() {
        return colWidth;
    }

    public void setColWidth(Integer colWidth) {
        this.colWidth = colWidth;
    }
}
