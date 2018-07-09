package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationNature;

public interface OrganizationNatureService {
	int deleteByPrimaryKey(Integer uid);

    int insert(OrganizationNature record);

    int insertSelective(OrganizationNature record);

    OrganizationNature selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(OrganizationNature record);

    int updateByPrimaryKey(OrganizationNature record);
    
    /**
     * 根据条件查询组织性质
     * @param organizationNature 条件
     * @return
     */
    public R queryOrgNaturesNotPage(OrganizationNature organizationNature) throws Exception;
}
