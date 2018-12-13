package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyOrgStep;

public interface JoinPartyOrgStepService {
	int deleteByPrimaryKey(Integer id);

    int insert(JoinPartyOrgStep record);

    int insertSelective(JoinPartyOrgStep record);

    JoinPartyOrgStep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JoinPartyOrgStep record);

    int updateByPrimaryKey(JoinPartyOrgStep record);
    
    /**
     * 查询申请入党人员进行步骤
     * @param conditions
     * @return
     */
    public R queryJoinPartyOrgSteps(Map<String, Object> conditions);
    
    /**
     * 变更此步骤的信息
     * @param jpos
     * @return
     */
    public R updateJoinPartyOrgSteps(JoinPartyOrgStep jpos);
    
    /**
     * 查询申请入党人员的信息
     * @param conditions
     * @return
     */
    public R queryUserJoinPartyOrgSteps(Map<String, Object> conditions);
}
