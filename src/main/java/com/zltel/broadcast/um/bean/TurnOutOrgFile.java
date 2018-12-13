package com.zltel.broadcast.um.bean;

import java.util.Date;

public class TurnOutOrgFile {
    private Integer id;

    private Integer stepId;

    private String fileTitle;

    private String fileDescribes;

    private String filePath;

    private String fileType;

    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle == null ? null : fileTitle.trim();
    }

    public String getFileDescribes() {
        return fileDescribes;
    }

    public void setFileDescribes(String fileDescribes) {
        this.fileDescribes = fileDescribes == null ? null : fileDescribes.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}