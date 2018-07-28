package com.zltel.broadcast.plansuggest.bean;
/**
 * 推荐计划信息
 * @author wangch
 *
 */
public class EventPlanSuggests {
    private Integer sugId;

    private String title;

    private String remark;

    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}