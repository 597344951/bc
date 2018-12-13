package com.zltel.broadcast.threeone.bean;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class ThreeoneSummaryWithBLOBs extends ThreeoneSummary {
    private String summary;

    private String annex;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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