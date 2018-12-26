package com.zltel.broadcast.um.service;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationInformation;

public interface OrganizationInformationService {
	int deleteByPrimaryKey(Integer orgInfoId);

    int insert(OrganizationInformation record);

    int insertSelective(OrganizationInformation record);

    OrganizationInformation selectByPrimaryKey(Integer orgInfoId);

    int updateByPrimaryKeySelective(OrganizationInformation record);

    int updateByPrimaryKeyWithBLOBs(OrganizationInformation record);

    int updateByPrimaryKey(OrganizationInformation record);
    
    /**
     * 组织信息
     * @param organizationInformation
     * @return
     */
    public List<OrganizationInformation> queryOrgInfosSelect(OrganizationInformation organizationInformation);
    
    /**
     * 查询组织信息（关联组织类型表的查询）
     * @param organizationInformation
     * @return
     */
    public List<Map<String, Object>> queryOrgInfosSelects(Map<String, Object> condition);
    
    /**
     * 组织信息
     * @param organizationInformation
     * @return
     */
    public R joinOrgQueryOrgInfosSelect(Map<String, Object> condition);
    
    /**
	 * 得到用户数量
	 * @param organizationInformation
	 * @return
	 */
	public R queryOrgRelationsNewForUserId(Map<String, Object> organizationInformation);
    
    /**
     * 根据条件查询组织信息
     * @param organizationInformation 条件
     * @return
     */
    public R queryOrgInfos(OrganizationInformation organizationInformation) throws Exception;
    
    
    /**
     * 根据条件查询组织信息
     * @param organizationInformation 条件
     * @return
     */
    public R queryOrgInfosForMap(Map<String, Object> organizationInformation, int pageNum, int pageSize) throws Exception;
    
    /**
     * 查询当前组织下的所有子组织
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    public R queryThisOrgChildren(Map<String, Object> organizationInformation, int pageNum, int pageSize) throws Exception;
    
    /**
     * 根据条件查询组织信息树
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    public R queryOrgInfosToTree(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 根据条件查询组织信息树
     * @param orgInfoConditions
     * @return
     * @throws Exception
     */
    public R queryOrgInfosToTree(Map<String, Object> orgInfoConditions);
    
    /**
     * 查询省份
     * @param organizationInformation
     * @return
     */
    public R queryOrgInfosCommitteeProvince(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 查询城市
     * @param organizationInformation
     * @return
     */
    public R queryOrgInfosCommitteeCity(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 查询地区
     * @param organizationInformation
     * @return
     */
    public R queryOrgInfosCommitteeArea(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 新增组织信息
     * @param OrganizationInformation
     * @return
     * @throws Exception
     */
    public R insertOrgInfo(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 修改党员信息
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    public R updateOrgInfo(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 删除组织
     * @param organizationInformation
     * @return
     * @throws Exception
     */
    public R deleteOrgInfo(OrganizationInformation organizationInformation) throws Exception;
    
    /**
     * 查询积分结构树
     * @param conditions
     * @return
     */
    public R queryOrgIntegralConstituteToTree(Map<String, Object> conditions);
    
    /**
     * 服务于按照时间间隔一次展示组织信息的层级，组织信息组织结构展示
     * @param conditions
     * @return
     */
    public R bossSayOneByOneShowOrgInfoLevel(Map<String, Object> conditions);
}
