package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationNature;

public interface OrganizationNatureMapper extends BaseDao<OrganizationNature> {
    int deleteByPrimaryKey(Integer orgNatureId);

    int insert(OrganizationNature record);

    int insertSelective(OrganizationNature record);

    OrganizationNature selectByPrimaryKey(Integer orgNatureId);

    int updateByPrimaryKeySelective(OrganizationNature record);

    int updateByPrimaryKey(OrganizationNature record);
    
    /**
     * 根据条件查询组织性质
     * @param organizationNature 条件
     * @return
     */
    public List<OrganizationNature> queryOrgNatures(OrganizationNature organizationNature);
}