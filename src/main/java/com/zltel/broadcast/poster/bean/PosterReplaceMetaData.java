package com.zltel.broadcast.poster.bean;

public class PosterReplaceMetaData {

    private Integer[] templateIds;
    private String search;
    private String rep;

    public Integer[] getTemplateIds() {
        return templateIds;
    }

    public void setTemplateIds(Integer[] templateIds) {
        this.templateIds = templateIds;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }
}
