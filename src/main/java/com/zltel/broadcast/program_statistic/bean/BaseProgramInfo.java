package com.zltel.broadcast.program_statistic.bean;

/**
 * 基础节目信息
 * 
 * @author iamwa
 *
 */
public class BaseProgramInfo {
    /** 终端代码 **/
    private String code;
    /** 节目Id */
    private Integer programId;

    /**
     * 模板Id
     */
    private Integer programTemplateId;
    /**
     * 节目名称
     */
    private String programName;
    /**
     * 推送时间
     */
    private String publishTime;
    /**
     * 节目有效时间
     */
    private String activeStartTime;
    /**
     * 节目截止时间
     */
    private String activeEndTime;
    /**
     * 星期
     */
    private String weeks;
    /**
     * 时间段
     */
    private String times;

    /**
     * 所属类别Id
     */
    private Integer categoryId;
    /**
     * 所属类别名称
     */
    private String categoryName;
    /**
     * 播放时间
     */
    private Integer playTime;
    /**
     * 分辨率
     */
    private String resolution;
    /**
     * 截止时间
     */
    private String expireTime;

    
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((programId == null) ? 0 : programId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseProgramInfo other = (BaseProgramInfo) obj;
        if (code == null) {
            if (other.code != null) return false;
        } else if (!code.equals(other.code)) return false;
        if (programId == null) {
            if (other.programId != null) return false;
        } else if (!programId.equals(other.programId)) return false;
        return true;
    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getProgramTemplateId() {
        return programTemplateId;
    }

    public void setProgramTemplateId(Integer programTemplateId) {
        this.programTemplateId = programTemplateId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getActiveStartTime() {
        return activeStartTime;
    }

    public void setActiveStartTime(String activeStartTime) {
        this.activeStartTime = activeStartTime;
    }

    public String getActiveEndTime() {
        return activeEndTime;
    }

    public void setActiveEndTime(String activeEndTime) {
        this.activeEndTime = activeEndTime;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

}
