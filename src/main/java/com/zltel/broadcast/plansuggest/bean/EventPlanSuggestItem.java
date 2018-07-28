package com.zltel.broadcast.plansuggest.bean;

public class EventPlanSuggestItem {
    private Integer id;

    private Integer eventId;

    private Integer orgId;

    private Integer sugId;

    private Integer type;

    private Integer orderNum;

    /** 懒加载 **/
    private EventPlanSuggests suggestInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the suggestInfo
     */
    public EventPlanSuggests getSuggestInfo() {
        return suggestInfo;
    }

    /**
     * @param suggestInfo the suggestInfo to set
     */
    public void setSuggestInfo(EventPlanSuggests suggestInfo) {
        this.suggestInfo = suggestInfo;
    }

}
