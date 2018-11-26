package com.zltel.broadcast.um.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationRelation;

public interface OrganizationRelationMapper extends BaseDao<OrganizationRelation> {
    int deleteByPrimaryKey(Integer orgRltId);

    int insert(OrganizationRelation record);

    int insertSelective(OrganizationRelation record);

    OrganizationRelation selectByPrimaryKey(Integer orgRltId);

    int updateByPrimaryKeySelective(OrganizationRelation record);

    int updateByPrimaryKey(OrganizationRelation record);
    
    /**
     * 根据条件查询组织关系
     * @param organizationRelation 条件
     * @return
     */
    public List<HashMap<String, Object>> queryOrgRelations(Map<String, Object> orgRelationConditiona);
    
    /**
     * 根据条件查询组织关系(新版组织)
     * @param organizationRelation 条件
     * @return
     */
    public List<Map<String, Object>> queryOrgRelationsNew(Map<String, Object> orgRelationConditiona);
    
    /**
     * 根据组织id删除组织关系
     * @param orgInfoId 条件
     * @return
     */
    public int deleteOrgRelationByOrgInfoId(Integer orgInfoId);
    
    /**
     * 根据职责id删除组织关系
     * @param orgDutyId 条件
     * @return
     */
    public int deleteOrgRelationByOrgDutyId(Integer orgDutyId);
    
    /**
     * 根据用户id删除组织关系
     * @param userId 条件
     * @return
     */
    public int deleteOrgRelationByUserId(Integer userId);
    
    /**
     * 查询组织关系
     * @param orgRelationConditiona 条件
     * @return
     */
    public List<Map<String, Object>> queryOrgRelationsNewForUserId(Map<String, Object> orgRelationConditiona);
    
    /**
     * 查询存在党员的组织
     * @param conditions 条件
     * @return
     */
    public List<Map<String, Object>> queryHavePartyUserOrg(Map<String, Object> conditions);
}