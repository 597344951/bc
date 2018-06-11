package com.zltel.broadcast.log.bean;

import java.util.Date;

public class Log {
    private Integer logId;

    private String username;

    private String operation;

    private String ip;

    private String level;

    private String method;

    private Date date;

    private String msg;

    private Long costtime;

    private Integer type;

    private String params;
    
    /**开始时间/结束时间**/
    private Date startTime;
    private Date endTime;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Long getCosttime() {
        return costtime;
    }

    public void setCosttime(Long costtime) {
        this.costtime = costtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 获取startTime  
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置startTime  
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取endTime  
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置endTime  
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}