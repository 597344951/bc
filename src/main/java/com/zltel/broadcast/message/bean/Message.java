package com.zltel.broadcast.message.bean;

import java.util.Date;

public class Message {
    /** 待审核 **/
    public static final int TYPE_VERIFY_PENDING = 1;
    /** 待办理 **/
    public static final int TYPE_HANDLE_PENDING = 2;
    /** 消息 **/
    public static final int TYPE_NOTICE = 3;
    /** 所有用户 **/
    public static final int USER_ALL = -1;
    /** 转台：未处理|处理 **/
    public static final int STATE_UNPROCESSED = 0;
    public static final int STATE_PROCESSED = 1;


    private Integer messageId;

    private Integer type;

    private String title;

    private String content;

    private Integer state;

    private Integer userId;

    private Integer sourceId;

    private Date addDate;

    private Date updateDate;

    public Message() {}

    public Message(Integer type, String title, String content, Integer userId, Integer sourceId) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.sourceId = sourceId;
        this.state = STATE_UNPROCESSED;
        this.updateDate = this.addDate = new Date();
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
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
}