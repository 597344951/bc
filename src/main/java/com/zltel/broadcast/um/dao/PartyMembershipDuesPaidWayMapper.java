package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.PartyMembershipDuesPaidWay;

public interface PartyMembershipDuesPaidWayMapper extends BaseDao<PartyMembershipDuesPaidWay> {
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
    public List<PartyMembershipDuesPaidWay> queryPMDWs(PartyMembershipDuesPaidWay partyMembershipDuesPaidWay);
}