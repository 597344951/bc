package com.zltel.broadcast.um.bean;

import java.util.Date;

public class DeedsImg {
    private Integer id;

    private Integer deedsUserId;

    private String titles;

    private String describes;

    private String paths;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeedsUserId() {
        return deedsUserId;
    }

    public void setDeedsUserId(Integer deedsUserId) {
        this.deedsUserId = deedsUserId;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles == null ? null : titles.trim();
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths == null ? null : paths.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}