package com.zltel.broadcast.incision.sola.bean;

/**
 * 执行日志
 * 
 * @author iamwa
 *
 */
public class ExecuteLog {
    /** 日志ID **/
    private String pkId;
    /** 指令名称 **/
    private String name;
    /** 发送时间 **/
    private String sendtime;
    /** 反馈时间 **/
    private String acesstime;
    /** 接收信息 **/
    private String remark;
    /** 执行状态 **/
    private String status;


    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getAcesstime() {
        return acesstime;
    }

    public void setAcesstime(String acesstime) {
        this.acesstime = acesstime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
