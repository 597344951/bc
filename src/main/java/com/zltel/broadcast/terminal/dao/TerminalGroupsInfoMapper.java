package com.zltel.broadcast.terminal.dao;

import java.util.List;

import com.zltel.broadcast.terminal.bean.TerminalGroupsInfo;

public interface TerminalGroupsInfoMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(TerminalGroupsInfo record);

    int insertSelective(TerminalGroupsInfo record);

    TerminalGroupsInfo selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TerminalGroupsInfo record);

    int updateByPrimaryKey(TerminalGroupsInfo record);
    
    public List<Integer> queryInfo(int gid) throws Exception;
    
}