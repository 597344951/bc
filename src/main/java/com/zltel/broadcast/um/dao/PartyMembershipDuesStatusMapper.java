package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;

public interface PartyMembershipDuesStatusMapper extends BaseDao<PartyMembershipDuesStatus> {
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
    public List<PartyMembershipDuesStatus> queryPMDSs(PartyMembershipDuesStatus partyMembershipDuesStatus);
}