package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationJoinProcess;

public interface OrganizationJoinProcessService {
	int deleteByPrimaryKey(Integer id);

    int insert(OrganizationJoinProcess record);

    int insertSelective(OrganizationJoinProcess record);

    OrganizationJoinProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationJoinProcess record);

    int updateByPrimaryKey(OrganizationJoinProcess record);
    
    /**
     * 添加组织流程
     * @param ojp
     * @return
     */
    public R insertOrganizationJoinProcess(Map<String, Object> condition) throws Exception;
    
    /**
     * 查询
     * @param ojp
     * @return
     */
    public R queryOjp(OrganizationJoinProcess ojp);
    
    /**
     * 查询
     * @param orgId
     * @return
     */
    public R queryOrgOjp(Map<String, Object> condition);
}
