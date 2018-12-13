package com.zltel.broadcast.learncenter.bean;

/** 学习进度 **/
public class LearningProgress {
    /** 当天获取积分 **/
    private int dayScores;
    /** 当天最多获取积分 **/
    private int dayTotalScores;
    /** 季度积分 **/
    private int quarterScores;
    /** 季度总积分 **/
    private int quarterTotalScores;
    /** 季度剩余天数 **/
    private int quarterDayLeaves;
    /**季度总天数**/
    private int quarterDayTotal;
    
    //积分数
    private int passageScore;
    private int videoScore;
    private int otherScore;

    public int getDayScores() {
        return dayScores;
    }

    public void setDayScores(int dayScores) {
        this.dayScores = dayScores;
    }

    public int getDayTotalScores() {
        return dayTotalScores;
    }

    public void setDayTotalScores(int dayTotalScores) {
        this.dayTotalScores = dayTotalScores;
    }

    public int getQuarterScores() {
        return quarterScores;
    }

    public void setQuarterScores(int quarterScores) {
        this.quarterScores = quarterScores;
    }

    public int getQuarterTotalScores() {
        return quarterTotalScores;
    }

    public void setQuarterTotalScores(int quarterTotalScores) {
        this.quarterTotalScores = quarterTotalScores;
    }

    public int getQuarterDayLeaves() {
        return quarterDayLeaves;
    }

    public void setQuarterDayLeaves(int quarterDayLeaves) {
        this.quarterDayLeaves = quarterDayLeaves;
    }

    public int getPassageScore() {
        return passageScore;
    }

    public void setPassageScore(int passageScore) {
        this.passageScore = passageScore;
    }

    public int getVideoScore() {
        return videoScore;
    }

    public void setVideoScore(int videoScore) {
        this.videoScore = videoScore;
    }

    public int getOtherScore() {
        return otherScore;
    }

    public void setOtherScore(int otherScore) {
        this.otherScore = otherScore;
    }

    public int getQuarterDayTotal() {
        return quarterDayTotal;
    }

    public void setQuarterDayTotal(int quarterDayTotal) {
        this.quarterDayTotal = quarterDayTotal;
    }

}
