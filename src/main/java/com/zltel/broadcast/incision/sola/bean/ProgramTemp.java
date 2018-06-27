package com.zltel.broadcast.incision.sola.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zltel.broadcast.incision.sola.utils.ResExtractUtil;

public class ProgramTemp {
    /** 节目ID **/
    private String programId;
    /** 节目名称 **/
    private String name;
    /** 节目所属分类 **/
    private String categoryId;
    private String categoryName;
    /** 节目类型（1：触模类节目，0,2,3非触模类节目） **/
    private String modelType;
    /** 节目分辨率 **/
    private String resolution;
    /** 节目播放时长 **/
    private String playTime;
    /** 节目状态（0为待审核 ；1为审核通过；2为审核未通过；-1为草稿；-2为待转换；-3为正在转换;-4为转换失败） **/
    private String pubStatus;
    /** 节目截图 **/
    private String coverImage;
    /** 节目创建时间 **/
    private String createTime;
    /** 节目截图地址 **/
    private String coverImageUrl;

     
    public String getModelTypeName() {
        return "1".equals(this.getModelType()) ? "触摸类节目" : "非触摸类节目";
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        this.coverImageUrl = ResExtractUtil.getImageUrl(coverImage);
    }


    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(String pubStatus) {
        this.pubStatus = pubStatus;
    }

    public String getCoverImage() {
        return coverImage;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

}
