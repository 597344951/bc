package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.PartyUser;

public interface PartyUserMapper extends BaseDao<PartyUser> {
	/**
     * 根据条件查询党员用户
     * @param partyUser
     * @return
     */
    public List<PartyUser> queryPartyUsers(PartyUser partyUser);
}