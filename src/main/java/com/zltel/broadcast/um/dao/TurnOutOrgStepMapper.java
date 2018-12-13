package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.TurnOutOrgStep;

public interface TurnOutOrgStepMapper extends BaseDao<TurnOutOrgStep> {
    int deleteByPrimaryKey(Integer id);

    int insert(TurnOutOrgStep record);

    int insertSelective(TurnOutOrgStep record);

    TurnOutOrgStep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnOutOrgStep record);

    int updateByPrimaryKey(TurnOutOrgStep record);
    
    /**
     * 查询申请转出人员的步骤信息
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryUserTurnOutOrgSteps(Map<String, Object> conditions);
}