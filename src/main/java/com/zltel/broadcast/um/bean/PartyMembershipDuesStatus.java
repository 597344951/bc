package com.zltel.broadcast.um.bean;

public class PartyMembershipDuesStatus {
    private Integer id;

    private String name;

    private String describes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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