package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationJoinProcess;

public interface OrganizationJoinProcessMapper extends BaseDao<OrganizationJoinProcess> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationJoinProcess record);

    int insertSelective(OrganizationJoinProcess record);

    OrganizationJoinProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationJoinProcess record);

    int updateByPrimaryKey(OrganizationJoinProcess record);
    
    /**
     * 根据组织id删除
     * @param orgId
     * @return
     */
    public int deleteByOrgId(Integer orgId);
    
    /**
     * 根据组织id删除
     * @param orgId
     * @return
     */
    public List<OrganizationJoinProcess> queryOjp(OrganizationJoinProcess ojp);
}