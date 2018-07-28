package com.zltel.broadcast.eventplan.bean;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.zltel.broadcast.planjoin.bean.ActivityRegistration;
import com.zltel.broadcast.planjoin.bean.ActivitySign;

public class EventPlanInfo {


    private Integer eventPlanId;
    @NotNull(message = "关联事件不能为空")
    private Integer eventId;
    @NotNull(message = "计划标题不能为空")
    private String title;
    @NotNull(message = "计划正文不能为空")
    private String content;

    @NotNull(message = "开始时间不能为")
    private Date stime;
    @NotNull(message = "结束时间不能为空")
    private Date etime;

    private Integer orgId;
    private Integer status;
    private Integer userId;


    private String pubTaskId;
    private Date saveTime;
    private String participateType;

    /** 多个status状态 **/
    private String[] statuss;
    /** 延迟加载状态信息 **/
    private EventPlanStatus planStatus;
    private List<CostPlan> costplans;
    
    /**用户投票信息**/
    private EventPlanVotingItem userVoting;
    /**用户报名信息**/
    private ActivityRegistration userRegist;
    /**用户签到信息**/
    private ActivitySign userSign;
    
    
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getPubTaskId() {
        return pubTaskId;
    }
    public void setPubTaskId(String pubTaskId) {
        this.pubTaskId = pubTaskId;
    }
    public Date getSaveTime() {
        return saveTime;
    }
    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }
    public String getParticipateType() {
        return participateType;
    }
    public void setParticipateType(String participateType) {
        this.participateType = participateType;
    }
    public String[] getStatuss() {
        return statuss;
    }
    public void setStatuss(String[] statuss) {
        this.statuss = statuss;
    }
    public EventPlanStatus getPlanStatus() {
        return planStatus;
    }
    public void setPlanStatus(EventPlanStatus planStatus) {
        this.planStatus = planStatus;
    }
    public List<CostPlan> getCostplans() {
        return costplans;
    }
    public void setCostplans(List<CostPlan> costplans) {
        this.costplans = costplans;
    }
    public EventPlanVotingItem getUserVoting() {
        return userVoting;
    }
    public void setUserVoting(EventPlanVotingItem userVoting) {
        this.userVoting = userVoting;
    }
    public ActivityRegistration getUserRegist() {
        return userRegist;
    }
    public void setUserRegist(ActivityRegistration userRegist) {
        this.userRegist = userRegist;
    }
    public ActivitySign getUserSign() {
        return userSign;
    }
    public void setUserSign(ActivitySign userSign) {
        this.userSign = userSign;
    }

 

}
