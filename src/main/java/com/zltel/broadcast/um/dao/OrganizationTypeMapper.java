package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationType;

public interface OrganizationTypeMapper extends BaseDao<OrganizationType> {
    int deleteByPrimaryKey(Integer orgTypeId);

    int insert(OrganizationType record);

    int insertSelective(OrganizationType record);

    OrganizationType selectByPrimaryKey(Integer orgTypeId);

    int updateByPrimaryKeySelective(OrganizationType record);

    int updateByPrimaryKey(OrganizationType record);
    
    /**
     * 根据条件查询组织类型
     * @param organizationType 条件
     * @return
     */
    public List<OrganizationType> queryOrgTypes(OrganizationType organizationType);
}