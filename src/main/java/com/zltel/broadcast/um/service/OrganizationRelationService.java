package com.zltel.broadcast.um.service;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationRelation;

public interface OrganizationRelationService {
	int deleteByPrimaryKey(Integer uid);

    int insert(OrganizationRelation record);

    int insertSelective(OrganizationRelation record);

    OrganizationRelation selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(OrganizationRelation record);

    int updateByPrimaryKey(OrganizationRelation record);
    
    /**
     * 查询组织关系
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgRelations(Map<String, Object> orgRelationConditiona, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询组织关系(新版)
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgRelationNews(Map<String, Object> orgRelationConditiona, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询组织关系(新版)
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgRelationNewsNotPage(Map<String, Object> orgRelationConditiona);
    
    /**
     * 查询组织关系
     * @param organizationRelation 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgRelationsNotPage(Map<String, Object> organizationRelation) throws Exception;
    
    
    /**
     * 删除组织关系
     * @param organizationRelation 要删除的组织关系
     * @return	
     */
    public R deleteOrgRelation(OrganizationRelation organizationRelation) throws Exception;
    
    /**
     * 根据组织id删除组织关系
     * @param organizationRelation 条件
     * @return
     */
    public R deleteOrgRelationByOrgInfoId(OrganizationRelation organizationRelation) throws Exception;
    
    /**
     * 根据职责id删除组织关系
     * @param organizationRelation 条件
     * @return
     */
    public R deleteOrgRelationByOrgDutyId(OrganizationRelation organizationRelation) throws Exception;
    
    /**
     * 根据用户id删除组织关系
     * @param organizationRelation 条件
     * @return
     */
    public R deleteOrgRelationByUserId(OrganizationRelation organizationRelation) throws Exception;
    
    /**
     * 新增组织关系
     * @param organizationRelation 要新增的组织关系
     * @return
     * @throws Exception
     */
    public R insertOrgRelation(OrganizationRelation organizationRelation, List<Integer> orgRltDutys) throws Exception;
    
    /**
     * 查询存在党员的组织
     * @param conditions 条件
     * @return
     */
    public R queryHavePartyUserOrg(Map<String, Object> conditions);
}
