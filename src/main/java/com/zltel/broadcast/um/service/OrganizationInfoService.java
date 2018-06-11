package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationInfo;

public interface OrganizationInfoService {
	int deleteByPrimaryKey(Integer uid);

    int insert(OrganizationInfo record);

    int insertSelective(OrganizationInfo record);

    OrganizationInfo selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(OrganizationInfo record);

    int updateByPrimaryKey(OrganizationInfo record);
    
    /**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgInfos(OrganizationInfo organizationInfo) throws Exception;
    
    /**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgInfosForInsertRelation(OrganizationInfo organizationInfo) throws Exception;
    
    /**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgInfosToTree() throws Exception;
    
    /**
     * 查询组织信息
     * @param organizationInfo 条件
     * @return	查询得到的组织信息
     */
    public R queryOrgInfosNotPage(OrganizationInfo organizationInfo) throws Exception;
    
    /**
     * 修改组织信息
     * @param organizationInfo 要修改的组织信息
     * @return	
     */
    public R updateOrgInfo(OrganizationInfo organizationInfo) throws Exception;
    
    /**
     * 删除组织信息
     * @param organizationInfo 要删除的组织信息
     * @return	
     */
    public R deleteOrgInfo(OrganizationInfo organizationInfo) throws Exception;
    
    /**
     * 新增组织信息
     * @param organizationInfo 要新增的组织信息
     * @return
     * @throws Exception
     */
    public R insertOrgInfo(OrganizationInfo organizationInfo) throws Exception;
}
