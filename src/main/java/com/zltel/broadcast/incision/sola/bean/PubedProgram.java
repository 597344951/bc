package com.zltel.broadcast.incision.sola.bean;

import com.zltel.broadcast.incision.sola.utils.ResExtractUtil;

/**
 * 已发布节目
 * @author wangch
 *
 */
public class PubedProgram {
    /** 节目ID **/
    private String pkId;
    /** 节目封面 **/
    private String coverImage;
    /** 节目名称 **/
    private String name;
    /** 发布类型（简单发布，频道发布，紧急发布，默认垫片） **/
    private String publishTypeStr;
    /** 有效日期 **/
    private String activeTime;
    /** 播放时间 **/
    private String playTime;
    /** 发布人 **/
    private String userName;
    /** 发布时间 **/
    private String createTime;
    
    /** 节目截图地址 **/
    private String coverImageUrl;
    /** -1屏幕不存在，0节目过期，1节目正在播放,2当前未在播放**/
    private String status;
    
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
        this.coverImageUrl = ResExtractUtil.getImageUrl(coverImage);
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishTypeStr() {
        return publishTypeStr;
    }

    public void setPublishTypeStr(String publishTypeStr) {
        this.publishTypeStr = publishTypeStr;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
