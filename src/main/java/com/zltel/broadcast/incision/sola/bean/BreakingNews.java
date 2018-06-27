package com.zltel.broadcast.incision.sola.bean;

/**
 * 紧急插播节目
 * 
 * @author wangch
 *
 */
public class BreakingNews {
    /** 消息ID **/
    private String pkId;
    /** 消息内容 **/
    private String contents;
    /** 插播时间 **/
    private String effectTime;
    /** 插播节目 **/
    private String screens;
    /** 插播人 **/
    private String userName;
    /** 创建时间 **/
    private String updateTime;
    /** 状态 **/
    private String status;
    
    
    public String getPkId() {
        return pkId;
    }
    public void setPkId(String pkId) {
        this.pkId = pkId;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getEffectTime() {
        return effectTime;
    }
    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }
    public String getScreens() {
        return screens;
    }
    public void setScreens(String screens) {
        this.screens = screens;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
