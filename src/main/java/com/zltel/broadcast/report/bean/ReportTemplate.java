package com.zltel.broadcast.report.bean;

import java.util.Date;

public class ReportTemplate {
    private Integer tpId;

    private Integer typeId;

    private Integer orgid;

    private Integer uid;

    private String title;

    private Date createtime;

    private String previewPicture;

    private String content;
    
    /** 关键词 **/
    private String keyword;

    public Integer getTpId() {
        return tpId;
    }

    public void setTpId(Integer tpId) {
        this.tpId = tpId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public String getPreviewPicture() {
        return previewPicture;
    }

    public void setPreviewPicture(String previewPicture) {
        this.previewPicture = previewPicture == null ? null : previewPicture.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}