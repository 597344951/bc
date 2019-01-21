package com.zltel.broadcast.um.bean;

import java.math.BigDecimal;

public class IntegralChangeScene {
    private Integer id;

    private Integer ictId;

    private String name;

    private BigDecimal score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIctId() {
        return ictId;
    }

    public void setIctId(Integer ictId) {
        this.ictId = ictId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}