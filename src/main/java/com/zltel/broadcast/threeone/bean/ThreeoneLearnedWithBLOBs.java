package com.zltel.broadcast.threeone.bean;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class ThreeoneLearnedWithBLOBs extends ThreeoneLearned {
    private String learned;

    private String annex;

    public String getLearned() {
        return learned;
    }

    public void setLearned(String learned) {
        this.learned = learned == null ? null : learned.trim();
    }

    public String getAnnex() {
        return annex;
    }

    public List<Map> getAnnexObject() {
        return JSON.parseArray(this.annex, Map.class);
    }

    public void setAnnex(String annex) {
        this.annex = annex == null ? null : annex.trim();
    }
}