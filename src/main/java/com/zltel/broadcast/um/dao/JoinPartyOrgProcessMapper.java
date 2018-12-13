package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.JoinPartyOrgProcess;

public interface JoinPartyOrgProcessMapper extends BaseDao<JoinPartyOrgProcess> {
    int deleteByPrimaryKey(Integer id);

    int insert(JoinPartyOrgProcess record);

    int insertSelective(JoinPartyOrgProcess record);

    JoinPartyOrgProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JoinPartyOrgProcess record);

    int updateByPrimaryKey(JoinPartyOrgProcess record);
    
    /**
     * 查询加入党的步骤
     * @param jpop
     * @return
     */
    public List<JoinPartyOrgProcess> queryJoinPartyOrgProcess(JoinPartyOrgProcess jpop);
    
    /**
     * 查询加入党的步骤
     * @param jpop
     * @return
     */
    public List<JoinPartyOrgProcess> queryJoinPartyOrgProcessByProcessId(JoinPartyOrgProcess jpop);
}