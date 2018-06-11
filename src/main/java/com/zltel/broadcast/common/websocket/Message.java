package com.zltel.broadcast.common.websocket;

import java.util.Date;

/**
 * Message class
 *
 * @author Touss
 * @date 2018/5/18
 */
public class Message {
    /** 发送者 **/
    private String sender;
    /** 消息内容 **/
    private String content;
    /** 连接 **/
    private String url;
    
    /**消息唯一编号**/
    private Integer mid;
    /**消息主题**/
    private String title;
    /**发生时间**/
    private Date time;
    
    
    public Message() {  }

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public Message(String sender, String content, String url) {
        this.sender = sender;
        this.content = content;
        this.url = url;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取title  
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title  
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取time  
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置time  
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取mid  
     * @return the mid
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * 设置mid  
     * @param mid the mid to set
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }
    
}
