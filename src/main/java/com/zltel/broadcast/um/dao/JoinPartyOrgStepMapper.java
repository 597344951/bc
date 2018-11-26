package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.JoinPartyOrgStep;

public interface JoinPartyOrgStepMapper extends BaseDao<JoinPartyOrgStep> {
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
    public List<Map<String, Object>> queryJoinPartyOrgSteps(Map<String, Object> conditions);
}