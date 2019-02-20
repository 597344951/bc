package com.zltel.broadcast.area_manage.bean;

import java.math.BigDecimal;

public class MapAreaLatLng {
    private Integer id;

    private Integer infoId;

    private BigDecimal radius;

    private BigDecimal lat;

    private BigDecimal lng;

    private BigDecimal southwestLat;

    private BigDecimal southwestLng;

    private BigDecimal northeastLat;

    private BigDecimal northeastLng;

    private Integer index;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getSouthwestLat() {
        return southwestLat;
    }

    public void setSouthwestLat(BigDecimal southwestLat) {
        this.southwestLat = southwestLat;
    }

    public BigDecimal getSouthwestLng() {
        return southwestLng;
    }

    public void setSouthwestLng(BigDecimal southwestLng) {
        this.southwestLng = southwestLng;
    }

    public BigDecimal getNortheastLat() {
        return northeastLat;
    }

    public void setNortheastLat(BigDecimal northeastLat) {
        this.northeastLat = northeastLat;
    }

    public BigDecimal getNortheastLng() {
        return northeastLng;
    }

    public void setNortheastLng(BigDecimal northeastLng) {
        this.northeastLng = northeastLng;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}