package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyBranchType;

public interface JoinPartyBranchTypeService {
	int deleteByPrimaryKey(Integer uid);

    int insert(JoinPartyBranchType record);

    int insertSelective(JoinPartyBranchType record);

    JoinPartyBranchType selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(JoinPartyBranchType record);

    int updateByPrimaryKey(JoinPartyBranchType record);
    
    /**
     * 查询加入党支部方式
     * @param joinPartyBranchType
     * @return
     */
    public R queryJoinPartyBranchTypes(JoinPartyBranchType joinPartyBranchType) throws Exception;
}
