package com.zltel.broadcast.um.bean;

public class FirstLineType {
    private Integer fltId;

    private String firstLineTypeName;

    private String describes;

    public Integer getFltId() {
        return fltId;
    }

    public void setFltId(Integer fltId) {
        this.fltId = fltId;
    }

    public String getFirstLineTypeName() {
        return firstLineTypeName;
    }

    public void setFirstLineTypeName(String firstLineTypeName) {
        this.firstLineTypeName = firstLineTypeName == null ? null : firstLineTypeName.trim();
    }

    public String getdescribes() {
        return describes;
    }

    public void setdescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }
}