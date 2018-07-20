package com.zltel.broadcast.eventplan.bean;

import java.util.List;

/** 投票结果 **/
public class VotingResult {
    private Integer eventPlanId;
    private Integer yesCount;
    private Integer noCount;
    private Integer total;

    private List<EventPlanVotingItem> datas;

    /**
     * @return the eventPlanId
     */
    public Integer getEventPlanId() {
        return eventPlanId;
    }

    /**
     * @param eventPlanId the eventPlanId to set
     */
    public void setEventPlanId(Integer eventPlanId) {
        this.eventPlanId = eventPlanId;
    }

    /**
     * @return the yesCount
     */
    public Integer getYesCount() {
        return yesCount;
    }

    /**
     * @param yesCount the yesCount to set
     */
    public void setYesCount(Integer yesCount) {
        this.yesCount = yesCount;
    }

    /**
     * @return the noCount
     */
    public Integer getNoCount() {
        return noCount;
    }

    /**
     * @param noCount the noCount to set
     */
    public void setNoCount(Integer noCount) {
        this.noCount = noCount;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the datas
     */
    public List<EventPlanVotingItem> getDatas() {
        return datas;
    }

    /**
     * @param datas the datas to set
     */
    public void setDatas(List<EventPlanVotingItem> datas) {
        this.datas = datas;
    }

    
     


}
