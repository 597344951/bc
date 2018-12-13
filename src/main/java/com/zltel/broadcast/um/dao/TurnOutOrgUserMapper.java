package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.TurnOutOrgUser;

public interface TurnOutOrgUserMapper extends BaseDao<TurnOutOrgUser> {
    int deleteByPrimaryKey(Integer id);

    int insert(TurnOutOrgUser record);

    int insertSelective(TurnOutOrgUser record);

    TurnOutOrgUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnOutOrgUser record);

    int updateByPrimaryKey(TurnOutOrgUser record);
    
    /**
     * 查询党员转出信息
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryToouIsTurnOut(TurnOutOrgUser toou);
    
    /**
     * 查询申请组织关系转移人员的信息
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryTurnOutOrgPartyUsers(Map<String, Object> conditions);
}