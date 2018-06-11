package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyUser;

public interface PartyUserService {
	int deleteByPrimaryKey(Integer uid);

    int insert(PartyUser record);

    int insertSelective(PartyUser record);

    PartyUser selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(PartyUser record);

    int updateByPrimaryKey(PartyUser record);
    
    /**
     * 查询党员用户信息
     * @param partyUser 条件
     * @return	查询得到的党员用户
     */
    public R queryPartyUsers(PartyUser partyUser, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询党员用户信息
     * @param partyUser 条件
     * @return	查询得到的党员用户
     */
    public R queryPartyUsersNotPage(PartyUser partyUser) throws Exception;
    
    /**
     * 修改党员用户信息
     * @param partyUser 要修改的党员用户
     * @return	
     */
    public R updatePartyUser(PartyUser partyUser) throws Exception;
    
    /**
     * 删除党员用户
     * @param partyUser 要删除的党员用户
     * @return	
     */
    public R deletePartyUser(PartyUser partyUser) throws Exception;
    
    /**
     * 新增党员用户
     * @param partyUser 要新增的党员用户
     * @return
     * @throws Exception
     */
    public R insertPartyUser(PartyUser partyUser) throws Exception;
}
