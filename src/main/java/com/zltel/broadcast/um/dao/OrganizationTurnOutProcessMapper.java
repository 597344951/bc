package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.OrganizationTurnOutProcess;

public interface OrganizationTurnOutProcessMapper extends BaseDao<OrganizationTurnOutProcess> {
    int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTurnOutProcess record);

    int insertSelective(OrganizationTurnOutProcess record);

    OrganizationTurnOutProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationTurnOutProcess record);

    int updateByPrimaryKey(OrganizationTurnOutProcess record);
    
    /**
     * 查询
     * @param orgId
     * @return
     */
    public List<Map<String, Object>> queryOrgTurnOutProcess(Map<String, Object> condition);
    
    /**
     * 查询
     * @param orgId
     * @return
     */
    public List<Map<String, Object>> queryOrgTurnOutProcessMaxProcess(Map<String, Object> condition);
}