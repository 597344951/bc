package com.zltel.broadcast.poster.bean;

public class PosterSize {
    private Integer id;

    private Integer width;

    private Integer height;

    private String horVer;

    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getHorVer() {
        return horVer;
    }

    public void setHorVer(String horVer) {
        this.horVer = horVer == null ? null : horVer.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}