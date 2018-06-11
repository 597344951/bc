package com.zltel.broadcast.um.bean;

public class AcademicDegreeLevel {
    private Integer adDAid;

    private String name;

    private String describes;

    public Integer getAdDAid() {
        return adDAid;
    }

    public void setAdDAid(Integer adDAid) {
        this.adDAid = adDAid;
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