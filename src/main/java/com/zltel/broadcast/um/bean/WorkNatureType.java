package com.zltel.broadcast.um.bean;

public class WorkNatureType {
    private Integer workNatureId;

    private String name;

    private Integer describes;

    public Integer getWorkNatureId() {
        return workNatureId;
    }

    public void setWorkNatureId(Integer workNatureId) {
        this.workNatureId = workNatureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getdescribes() {
        return describes;
    }

    public void setdescribes(Integer describes) {
        this.describes = describes;
    }
}