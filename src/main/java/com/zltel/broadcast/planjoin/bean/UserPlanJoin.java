package com.zltel.broadcast.planjoin.bean;

import java.util.Date;

public class UserPlanJoin {
    private Integer userId;
    private Integer orgId;

    /** 筛选 开始时间 **/
    private Date stime;
    /** 筛选 结束时间 **/
    private Date etime;


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

  
    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

}
