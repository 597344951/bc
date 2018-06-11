package com.zltel.broadcast.eventplan.bean;

import javax.validation.constraints.NotNull;

public class CostPlan {
    private Integer id;

    @NotNull(message = "所属计划不能为空")
    private Integer eventPlanId;
    @NotNull(message = "花费类型不能为空")
    private Integer costType;
    @NotNull(message = "花费名称不能为空")
    private String name;
    @NotNull(message = "花费金额不能为空")
    private Float value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventPlanId() {
        return eventPlanId;
    }

    public void setEventPlanId(Integer eventPlanId) {
        this.eventPlanId = eventPlanId;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}