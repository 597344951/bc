package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;

public interface PartyMembershipDuesStatusService {
	int deleteByPrimaryKey(Integer id);

    int insert(PartyMembershipDuesStatus record);

    int insertSelective(PartyMembershipDuesStatus record);

    PartyMembershipDuesStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PartyMembershipDuesStatus record);

    int updateByPrimaryKey(PartyMembershipDuesStatus record);
    
    /**
     * 党费缴纳状态
     * @param partyMembershipDuesStatus
     * @return
     */
    public R queryPMDSs(PartyMembershipDuesStatus partyMembershipDuesStatus);
}
