package com.zltel.broadcast.eventplan.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class EventPlanInfo {
    private Integer eventPlanId;
    @NotNull(message = "关联事件不能为空")
    private Integer eventId;
    @NotNull(message = "计划标题不能为空")
    private String title;
    @NotNull(message = "计划正文不能为空")
    private String content;
    
    @NotNull(message = "开始时间不能为空")
    private Date stime;
    @NotNull(message = "结束时间不能为空")
    private Date etime;

    private Integer orgId;
    private Integer status;
    private Integer userId;

    private List<CostPlan> costplans;

    public Integer getEventPlanId() {
        return eventPlanId;
    }

    public void setEventPlanId(Integer eventPlanId) {
        this.eventPlanId = eventPlanId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取costplans  
     * @return the costplans
     */
    public List<CostPlan> getCostplans() {
        return costplans;
    }

    /**
     * 设置costplans  
     * @param costplans the costplans to set
     */
    public void setCostplans(List<CostPlan> costplans) {
        this.costplans = costplans;
    }

    /**
     * 获取stime  
     * @return the stime
     */
    public Date getStime() {
        return stime;
    }

    /**
     * 设置stime  
     * @param stime the stime to set
     */
    public void setStime(Date stime) {
        this.stime = stime;
    }

    /**
     * 获取etime  
     * @return the etime
     */
    public Date getEtime() {
        return etime;
    }

    /**
     * 设置etime  
     * @param etime the etime to set
     */
    public void setEtime(Date etime) {
        this.etime = etime;
    }
    
}
