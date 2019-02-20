package com.zltel.broadcast.program_statistic.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "id", allowGetters = true)
public class ProgramStatistic {
    private Long id;

    private String code;

    private Integer programId;

    private Integer programTemplateId;

    private String programPage;

    private String programName;

    private Date startTime;

    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getProgramTemplateId() {
        return programTemplateId;
    }

    public void setProgramTemplateId(Integer programTemplateId) {
        this.programTemplateId = programTemplateId;
    }

    public String getProgramPage() {
        return programPage;
    }

    public void setProgramPage(String programPage) {
        this.programPage = programPage == null ? null : programPage.trim();
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName == null ? null : programName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
