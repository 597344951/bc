package com.zltel.broadcast.template.bean;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class TemplateContent {
    private Integer tpId;
    @NotNull(message="模板分类不能为空")
    private Integer tpTypeId;
    //不能更改的参数
    private Integer orgid;
    private Integer uid;
    @NotBlank(message="模板标题不能为空")
    private String title;

    private Date createtime;
    @NotBlank(message="模板内容不能为空")
    private String content;

    private String previewPicture;
    /**关联节目id**/
    @NotBlank(message="关联节目模版不能为空")
    private String programTemplate;
    /**关联节目所属分类id**/
    @NotBlank(message="关联节目模版类别不能为空")
    private String categoryId;
    
    public Integer getTpId() {
        return tpId;
    }

    public void setTpId(Integer tpId) {
        this.tpId = tpId;
    }

    public Integer getTpTypeId() {
        return tpTypeId;
    }

    public void setTpTypeId(Integer tpTypeId) {
        this.tpTypeId = tpTypeId;
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPreviewPicture() {
        return previewPicture;
    }

    public void setPreviewPicture(String previewPicture) {
        this.previewPicture = previewPicture;
    }

    public String getProgramTemplate() {
        return programTemplate;
    }

    public void setProgramTemplate(String programTemplate) {
        this.programTemplate = programTemplate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
}