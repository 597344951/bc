package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationDuty;

public interface OrganizationDutyMapper extends BaseDao<OrganizationDuty> {
    int deleteByPrimaryKey(Integer orgDutyId);

    int insert(OrganizationDuty record);

    int insertSelective(OrganizationDuty record);

    OrganizationDuty selectByPrimaryKey(Integer orgDutyId);

    int updateByPrimaryKeySelective(OrganizationDuty record);

    int updateByPrimaryKey(OrganizationDuty record);
    
    /**
     * 根据条件查询组织职责
     * @param organizationDuty 条件
     * @return
     */
    public List<OrganizationDuty> queryOrgDutys(OrganizationDuty organizationDuty);
    
    
    /**
     * 根据条件查询组织职责
     * @param organizationDuty 条件
     * @return
     */
    public List<OrganizationDuty> queryOrgDutyForOrgInfoClick(OrganizationDuty organizationDuty);
    
    /**
     * 根据条件查询组织职责
     * @param organizationDuty 条件
     * @return
     */
    public List<OrganizationDuty> queryOrgDutysOfQueryRelations(OrganizationDuty organizationDuty);
}