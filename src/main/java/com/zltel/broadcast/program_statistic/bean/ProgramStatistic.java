package com.zltel.broadcast.program_statistic.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "id", allowGetters = true)
public class ProgramStatistic extends BaseProgramInfo {
    private Long id;

    private String programPage;

    private Date startTime;

    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getProgramPage() {
        return programPage;
    }

    public void setProgramPage(String programPage) {
        this.programPage = programPage == null ? null : programPage.trim();
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
