package com.zltel.broadcast.incision.sola.bean;

import com.zltel.broadcast.incision.sola.utils.ResExtractUtil;

/**
 * 终端信息
 * 
 * @author wangch
 *
 */
public class Screen {
    /** 终端ID **/
    private String pkId;
    /** 终端标识Code **/
    private String code;
    /** 终端分类ID **/
    private String categoryId;
    /** 终端名称 **/
    private String name;
    /** 屏幕尺寸 **/
    private String screenSize;
    /** 屏幕方向（横屏，竖屏） **/
    private String screenDirection;
    /** 屏幕交互（触摸，非触摸） **/
    private String screenInteraction;
    /** 终端类型（一体机，播放盒+显示屏）//1：一体机2：播放盒+显示屏3：播放盒+投影仪 **/
    private String screenType;
    /** 所属位置信息 **/
    private String position;
    /** 屏幕分辨率 **/
    private String resolution;
    /** 终端IP地址 **/
    private String ip;
    /** 终端mac地址 **/
    private String mac;
    /** 终端注册时间 **/
    private String regDateTime;
    /** 终端版本 **/
    private String version;
    /** 终端是否在线（1为在线，0为离线） **/
    private String onlineStatus;
    /** 最后在线时间 **/
    private String lastOnlineTime;
    /** 封面截图 **/
    private String coverImage;
    
    /**同步来源域名地址**/
    private String solaUrl;
    
    public String getPkId() {
        return pkId;
    }
    public void setPkId(String pkId) {
        this.pkId = pkId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getScreenSize() {
        return screenSize;
    }
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }
    public String getScreenDirection() {
        return screenDirection;
    }
    public void setScreenDirection(String screenDirection) {
        this.screenDirection = screenDirection;
    }
    public String getScreenInteraction() {
        return screenInteraction;
    }
    public void setScreenInteraction(String screenInteraction) {
        this.screenInteraction = screenInteraction;
    }
    public String getScreenType() {
        return screenType;
    }
    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getResolution() {
        return resolution;
    }
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getRegDateTime() {
        return regDateTime;
    }
    public void setRegDateTime(String regDateTime) {
        this.regDateTime = regDateTime;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public String getLastOnlineTime() {
        return lastOnlineTime;
    }
    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }
    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    public String getSolaUrl() {
        return solaUrl;
    }
    public void setSolaUrl(String solaUrl) {
        this.solaUrl = solaUrl;
        this.coverImage = ResExtractUtil.repResDomain(this.coverImage, this.solaUrl);
    }
    
}
