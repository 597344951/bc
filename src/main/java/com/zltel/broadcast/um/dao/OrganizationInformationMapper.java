package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationInformation;

public interface OrganizationInformationMapper extends BaseDao<OrganizationInformation>  {
    int deleteByPrimaryKey(Integer orgInfoId);

    int insert(OrganizationInformation record);

    int insertSelective(OrganizationInformation record);

    OrganizationInformation selectByPrimaryKey(Integer orgInfoId);

    int updateByPrimaryKeySelective(OrganizationInformation record);

    int updateByPrimaryKeyWithBLOBs(OrganizationInformation record);

    int updateByPrimaryKey(OrganizationInformation record);
    
    /**
     * 根据条件查询组织信息
     * @param organizationInformation 条件
     * @return
     */
    public List<OrganizationInformation> queryOrgInfos(OrganizationInformation organizationInformation);
    
    /**
     * 根据条件查询组织信息
     * @param organizationInformation 条件
     * @return
     */
    public List<Map<String, Object>> queryOrgInfosForMap(Map<String, Object> organizationInformation);
    
    /**
     * 查询省份
     * @param organizationInformation
     * @return
     */
    public List<OrganizationInformation> queryOrgInfosCommitteeProvince(OrganizationInformation organizationInformation);
    
    /**
     * 查询城市
     * @param organizationInformation
     * @return
     */
    public List<OrganizationInformation> queryOrgInfosCommitteeCity(OrganizationInformation organizationInformation);
    
    /**
     * 查询地区
     * @param organizationInformation
     * @return
     */
    public List<OrganizationInformation> queryOrgInfosCommitteeArea(OrganizationInformation organizationInformation);
    
    /**
     * 查询地区
     * @param organizationInformation
     * @return
     */
    public Integer queryInsertedOrgId();
}