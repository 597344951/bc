package com.zltel.broadcast.resource.bean;

import java.util.Date;

import com.zltel.broadcast.um.bean.SysUser;

public class Material {
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_PICTURE = "picture";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_TEXT = "text";
    /**资源管理 添加**/
    public static final Integer REASON_RESOURCE_MANAGE = 0;
    public static final Integer REASON_CONTENT_MAKER = 1;
    public static final Integer REASON_JOB_CALLBACK = 2;

    private Integer materialId;

    private String name;

    private String type;

    private String url;

    private String description;

    private Integer userId;

    private Integer orgId;

    private Integer uploadReason;

    private Integer relateContentId;

    private Integer limitContentId;

    private Integer examineState;

    private Date addDate;

    private Date updateDate;

    private Integer albumId;

    private String content;
    /** 关键词 **/
    private String keyword;

    private String viewUrl;


    public String getViewUrl() {
        if (TYPE_PICTURE.equals(type)) {
            viewUrl = "/material/image/" + this.materialId;
        } else {
            viewUrl = "/material/download/" + this.materialId;
        }
        return this.viewUrl;
    }

    public Material() {}

    public Material(Integer userId, Integer orgId) {
        super();
        this.userId = userId;
        this.orgId = orgId;
    }

    public Material(SysUser user) {
        super();
        this.userId = user.getUserId();
        this.orgId = user.getOrgId();
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
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

    public String getUrl() {
        return url;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Integer getUploadReason() {
        return uploadReason;
    }

    public void setUploadReason(Integer uploadReason) {
        this.uploadReason = uploadReason;
    }

    public Integer getRelateContentId() {
        return relateContentId;
    }

    public void setRelateContentId(Integer relateContentId) {
        this.relateContentId = relateContentId;
    }

    public Integer getLimitContentId() {
        return limitContentId;
    }

    public void setLimitContentId(Integer limitContentId) {
        this.limitContentId = limitContentId;
    }

    public Integer getExamineState() {
        return examineState;
    }

    public void setExamineState(Integer examineState) {
        this.examineState = examineState;
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

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
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
