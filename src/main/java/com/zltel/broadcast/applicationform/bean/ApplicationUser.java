package com.zltel.broadcast.applicationform.bean;

import java.util.Date;
import java.util.List;

import com.zltel.broadcast.um.bean.SysUser;

public class ApplicationUser {
    private Integer userFormId;

    private Integer formId;

    private Integer userId;

    private Integer orgId;

    private Date saveTime;

    private List<ApplicationUserData> datas;
    
    private List<ApplicationFormFields> fields;
    
    private ApplicationForm form;
    
    private SysUser user;

    public ApplicationUser(SysUser user) {
        super();
        this.userId = user.getUserId();
        this.orgId = user.getOrgId();
    }

    public ApplicationUser() {
        super();
    }

    public Integer getUserFormId() {
        return userFormId;
    }

    public void setUserFormId(Integer userFormId) {
        this.userFormId = userFormId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
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

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public List<ApplicationUserData> getDatas() {
        return datas;
    }

    public ApplicationForm getForm() {
        return form;
    }

    public void setForm(ApplicationForm form) {
        this.form = form;
    }

    public void setDatas(List<ApplicationUserData> datas) {
        this.datas = datas;
    }

    public List<ApplicationFormFields> getFields() {
        return fields;
    }

    public void setFields(List<ApplicationFormFields> fields) {
        this.fields = fields;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
