package com.zltel.broadcast.um.service;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationRelation;
import com.zltel.broadcast.um.bean.TurnOutOrgUser;

public interface TurnOutOrgUserService {
	int deleteByPrimaryKey(Integer id);

    int insert(TurnOutOrgUser record);

    int insertSelective(TurnOutOrgUser record);

    TurnOutOrgUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnOutOrgUser record);

    int updateByPrimaryKey(TurnOutOrgUser record);
    
    /**
     * 组织关系转移-选择党组织
     * @param conditions
     * @return
     */
    public R insertToou(TurnOutOrgUser toou);
    
    /**
     * 查询申请组织关系转移人员的信息
     * @param conditions
     * @return
     */
    public R queryTurnOutOrgPartyUsers(Map<String, Object> conditions, int pageNum, int pageSize);
    
    /**
     * 增加步骤
     * @param submitDate
     * @return
     */
    public R insertTurnOutOrgPartyStep(String submitDate);
    
    /**
     * 组织关系审核通过，确认加入此组织
     * @param organizationRelation
     * @param orgRltDutys
     * @return
     * @throws Exception
     */
    public R insertOrgRelation(Integer turnOutId, OrganizationRelation organizationRelation, List<Integer> orgRltDutys) throws Exception;

    /**
     * 查询转移步骤
     * @param toop
     * @return
     */
    public R queryToopOtherOrg(Map<String, Object> condition);
}
