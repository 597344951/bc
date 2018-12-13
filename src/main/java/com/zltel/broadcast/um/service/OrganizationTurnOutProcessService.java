package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.OrganizationTurnOutProcess;

public interface OrganizationTurnOutProcessService {
	int deleteByPrimaryKey(Integer id);

    int insert(OrganizationTurnOutProcess record);

    int insertSelective(OrganizationTurnOutProcess record);

    OrganizationTurnOutProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrganizationTurnOutProcess record);

    int updateByPrimaryKey(OrganizationTurnOutProcess record);
    
    /**
     * 创建组织时默认创建此组织的步骤
     * @return
     */
    public R establishOrgInsertProcess(int orgId) throws Exception;
    
    /**
     * 查询
     * @param orgId
     * @return
     */
    public R queryOrgTurnOutProcess(Map<String, Object> condition);
}
