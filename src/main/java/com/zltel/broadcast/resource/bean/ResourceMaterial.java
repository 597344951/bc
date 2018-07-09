package com.zltel.broadcast.resource.bean;

import java.util.Date;

import com.zltel.broadcast.um.bean.SysUser;

public class ResourceMaterial {
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_PICTURE = "image";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_TEXT = "text";
    
    
    private Integer materialId;

    private String name;

    private String type;

    private String description;

    private String url;

    private String coverUrl;

    private String size;

    private String contentType;

    private String timeLength;

    private Integer userId;

    private Integer orgId;

    private Integer albumId;

    private Date addDate;
    
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

    public ResourceMaterial() {}

    public ResourceMaterial(Integer userId, Integer orgId) {
        super();
        this.userId = userId;
        this.orgId = orgId;
    }

    public ResourceMaterial(SysUser user) {
        super();
        this.userId = user.getUserId();
        this.orgId = user.getOrgId();
    }
    private String content;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl == null ? null : coverUrl.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength == null ? null : timeLength.trim();
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

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}