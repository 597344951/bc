package com.zltel.broadcast.um.bean;

public class NationType {
    private Integer ntId;

    private String name;

    private String romeSpell;

    private String alphabetCode;

    private String describes;

    public Integer getNtId() {
        return ntId;
    }

    public void setNtId(Integer ntId) {
        this.ntId = ntId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRomeSpell() {
        return romeSpell;
    }

    public void setRomeSpell(String romeSpell) {
        this.romeSpell = romeSpell == null ? null : romeSpell.trim();
    }

    public String getAlphabetCode() {
        return alphabetCode;
    }

    public void setAlphabetCode(String alphabetCode) {
        this.alphabetCode = alphabetCode == null ? null : alphabetCode.trim();
    }

    public String getdescribes() {
        return describes;
    }

    public void setdescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }
}