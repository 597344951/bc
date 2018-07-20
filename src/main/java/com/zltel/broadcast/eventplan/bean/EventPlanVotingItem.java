package com.zltel.broadcast.eventplan.bean;

import javax.validation.constraints.NotNull;

public class EventPlanVotingItem extends EventPlanVotingItemKey {
    public static final Integer YES = 1;
    public static final Integer NO = -1;
    
    @NotNull(message="投票选择不能为空")
    private Integer yesOrNo;

    public Integer getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(Integer yesOrNo) {
        this.yesOrNo = yesOrNo;
    }
}
