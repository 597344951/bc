package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyOrgUser;

public interface JoinPartyOrgUserService {
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
    public R queryJoinPartyOrgUsers(Map<String, Object> conditions, int pageNum, int pageSize);
    
    /**
     * 申请入党
     * @param conditions
     * @return
     */
    public R insertJoinPartyOrgUsers(Map<String, Object> conditions);
}
