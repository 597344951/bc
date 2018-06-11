package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationType;

public interface OrganizationTypeService {
	int deleteByPrimaryKey(Integer uid);

    int insert(OrganizationType record);

    int insertSelective(OrganizationType record);

    OrganizationType selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(OrganizationType record);

    int updateByPrimaryKey(OrganizationType record);
    
    /**
     * 查询组织类型
     * @param organizationType 条件
     * @return	查询得到的组织类型
     */
    public R queryOrgTypes(OrganizationType organizationType, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询组织类型
     * @param organizationType 条件
     * @return	查询得到的组织类型
     */
    public R queryOrgTypesNotPage(OrganizationType organizationType) throws Exception;
}
