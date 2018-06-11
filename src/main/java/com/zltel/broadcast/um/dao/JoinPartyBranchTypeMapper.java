package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.JoinPartyBranchType;

public interface JoinPartyBranchTypeMapper extends BaseDao<JoinPartyBranchType> {
    int deleteByPrimaryKey(Integer jpbtId);

    int insert(JoinPartyBranchType record);

    int insertSelective(JoinPartyBranchType record);

    JoinPartyBranchType selectByPrimaryKey(Integer jpbtId);

    int updateByPrimaryKeySelective(JoinPartyBranchType record);

    int updateByPrimaryKey(JoinPartyBranchType record);
    
    /**
     * 查询加入党支部方式
     * @param joinPartyBranchType
     * @return
     */
    public List<JoinPartyBranchType> queryJoinPartyBranchTypes(JoinPartyBranchType joinPartyBranchType);
}