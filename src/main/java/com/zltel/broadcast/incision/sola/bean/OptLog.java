package com.zltel.broadcast.incision.sola.bean;

/**
 * 操作日志
 * 
 * @author wangch
 *
 */
public class OptLog {
    
    /** 日志ID **/
    private String pkId;
    /** 操作信息描述 **/
    private String remark;
    /** 操作时间 **/
    private String createTime;
    /** 操作用户 **/
    private String userName;
    /** 操作机器IP **/
    private String ip;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
