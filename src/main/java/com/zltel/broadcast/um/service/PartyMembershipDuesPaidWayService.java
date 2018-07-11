package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyMembershipDuesPaidWay;

public interface PartyMembershipDuesPaidWayService {
	int deleteByPrimaryKey(Integer id);

    int insert(PartyMembershipDuesPaidWay record);

    int insertSelective(PartyMembershipDuesPaidWay record);

    PartyMembershipDuesPaidWay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PartyMembershipDuesPaidWay record);

    int updateByPrimaryKey(PartyMembershipDuesPaidWay record);
    
    /**
     * 党费缴纳方式
     * @param partyMembershipDuesPaidWay
     * @return
     */
    public R queryPMDWs(PartyMembershipDuesPaidWay partyMembershipDuesPaidWay);
}
