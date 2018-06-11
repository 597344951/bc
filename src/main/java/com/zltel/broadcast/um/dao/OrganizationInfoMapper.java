package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationInfo;

public interface OrganizationInfoMapper extends BaseDao<OrganizationInfo> {
    int deleteByPrimaryKey(Integer orgInfoId);

    int insert(OrganizationInfo record);

    int insertSelective(OrganizationInfo record);

    OrganizationInfo selectByPrimaryKey(Integer orgInfoId);

    int updateByPrimaryKeySelective(OrganizationInfo record);

    int updateByPrimaryKey(OrganizationInfo record);
    
    /**
     * 根据条件查询组织信息
     * @param organizationInfo 条件
     * @return
     */
    public List<OrganizationInfo> queryOrgInfos(OrganizationInfo organizationInfo);
}