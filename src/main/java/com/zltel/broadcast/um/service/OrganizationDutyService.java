package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationDuty;

public interface OrganizationDutyService {
	int deleteByPrimaryKey(Integer uid);

    int insert(OrganizationDuty record);

    int insertSelective(OrganizationDuty record);

    OrganizationDuty selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(OrganizationDuty record);

    int updateByPrimaryKey(OrganizationDuty record);
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
    public R queryOrgDutys(OrganizationDuty organizationDuty, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
    public R queryOrgDutyForOrgInfoClick(OrganizationDuty organizationDuty) throws Exception;
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
	public R queryOrgDutyTreeForOrgInfo (OrganizationDuty organizationDuty) throws Exception;
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
    public R queryOrgDutysOfQueryRelations(OrganizationDuty organizationDuty) throws Exception;
    
    /**
     * 查询组织职责
     * @param organizationDuty 条件
     * @return	查询得到的组织职责
     */
    public R queryOrgDutysNotPage(OrganizationDuty organizationDuty) throws Exception;
    
    /**
     * 修改组织职责
     * @param organizationDuty 要修改的组织职责
     * @return	
     */
    public R updateOrgDuty(OrganizationDuty organizationDuty) throws Exception;
    
    /**
     * 删除组织职责
     * @param organizationDuty 要删除的组织职责
     * @return	
     */
    public R deleteOrgDuty(OrganizationDuty organizationDuty) throws Exception;
    
    /**
     * 新增组织职责
     * @param organizationDuty 要新增的组织职责
     * @return
     * @throws Exception
     */
    public R insertOrgDuty(OrganizationDuty organizationDuty) throws Exception;
}
