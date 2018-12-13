package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.TurnOutOrgStep;

public interface TurnOutOrgStepService {
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
    public R queryUserTurnOutOrgSteps(Map<String, Object> conditions);
    
    /**
     * 变更此步骤信息
     * @param toos
     * @return
     */
    public R updateTurnOutOrgPartySteps(boolean haveThisOrg, TurnOutOrgStep toos) throws Exception;
    
    /**
     * 补充材料
     * @param condition
     * @return
     */
    public R supplementFiles(String condition);
}
