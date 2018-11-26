package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.JoinPartyOrgUser;

public interface JoinPartyOrgUserMapper extends BaseDao<JoinPartyOrgUser> {
    int deleteByPrimaryKey(Integer id);

    int insert(JoinPartyOrgUser record);

    int insertSelective(JoinPartyOrgUser record);

    JoinPartyOrgUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JoinPartyOrgUser record);

    int updateByPrimaryKey(JoinPartyOrgUser record);
    
    /**
     * 查询申请入党人员的信息
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryJoinPartyOrgUsers(Map<String, Object> conditions);
}