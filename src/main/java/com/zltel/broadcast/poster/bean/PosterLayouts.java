package com.zltel.broadcast.poster.bean;

public class PosterLayouts {
    private Integer templateId;

    private String layouts;

    public PosterLayouts(Integer arg1, String arg2) {
        super();
        this.templateId = arg1;
        this.layouts = arg2;
    }

    public PosterLayouts() {}

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getLayouts() {
        return layouts;
    }

    public void setLayouts(String layouts) {
        this.layouts = layouts == null ? null : layouts.trim();
    }
}
