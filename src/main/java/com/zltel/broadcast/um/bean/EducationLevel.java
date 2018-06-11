package com.zltel.broadcast.um.bean;

public class EducationLevel {
    private Integer eduLevelEid;

    private String name;

    private String describes;

    public Integer getEduLevelEid() {
        return eduLevelEid;
    }

    public void setEduLevelEid(Integer eduLevelEid) {
        this.eduLevelEid = eduLevelEid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getdescribes() {
        return describes;
    }

    public void setdescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }
}