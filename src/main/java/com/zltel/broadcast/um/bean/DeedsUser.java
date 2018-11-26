package com.zltel.broadcast.um.bean;

import java.util.Date;

public class DeedsUser {
    private Integer id;

    private Integer userId;

    private Integer deedsTypeId;

    private Date occurrenceTime;

    private Date time;

    private String deedsTitle;
    
    private String deedsDetail;
    
    private String similarId;

    public String getSimilarId() {
		return similarId;
	}

	public void setSimilarId(String similarId) {
		this.similarId = similarId;
	}

	public String getDeedsTitle() {
		return deedsTitle;
	}

	public void setDeedsTitle(String deedsTitle) {
		this.deedsTitle = deedsTitle;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeedsTypeId() {
        return deedsTypeId;
    }

    public void setDeedsTypeId(Integer deedsTypeId) {
        this.deedsTypeId = deedsTypeId;
    }

    public Date getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDeedsDetail() {
        return deedsDetail;
    }

    public void setDeedsDetail(String deedsDetail) {
        this.deedsDetail = deedsDetail == null ? null : deedsDetail.trim();
    }
}