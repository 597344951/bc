package com.zltel.broadcast.terminal.m.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TerminalMGroups {
    private Integer id;

    private String label;

    private String description;

    private Integer parentId;

    private Date addDate;

    private Date updateDate;

    private List<TerminalMGroups> children;

    public TerminalMGroups() {
    }

    public TerminalMGroups(Integer id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getAddDate() {
        return addDate == null ? new Date(System.currentTimeMillis()) : addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<TerminalMGroups> getChildren() {
        return children;
    }

    public void setChildren(List<TerminalMGroups> children) {
        this.children = children;
    }

    public void addChild(TerminalMGroups terminalMGroups) {
        if (this.children == null) this.children = new ArrayList<>();
        this.children.add(terminalMGroups);
    }
}