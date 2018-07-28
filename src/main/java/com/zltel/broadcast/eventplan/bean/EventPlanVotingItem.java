package com.zltel.broadcast.eventplan.bean;

import javax.validation.constraints.NotNull;

import com.zltel.broadcast.um.bean.SysUser;

public class EventPlanVotingItem extends EventPlanVotingItemKey {
    public static final Integer YES = 1;
    public static final Integer NO = -1;
    
    private SysUser userInfo;
    
    @NotNull(message="投票选择不能为空")
    private Integer yesOrNo;

    public Integer getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(Integer yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    /**
     * @return the userInfo
     */
    public SysUser getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(SysUser userInfo) {
        this.userInfo = userInfo;
    }
    
    
    
}
