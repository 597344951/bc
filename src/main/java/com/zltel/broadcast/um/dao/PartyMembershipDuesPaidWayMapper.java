package com.zltel.broadcast.um.dao;

import com.zltel.broadcast.um.bean.PartyMembershipDuesPaidWay;

public interface PartyMembershipDuesPaidWayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PartyMembershipDuesPaidWay record);

    int insertSelective(PartyMembershipDuesPaidWay record);

    PartyMembershipDuesPaidWay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PartyMembershipDuesPaidWay record);

    int updateByPrimaryKey(PartyMembershipDuesPaidWay record);
}