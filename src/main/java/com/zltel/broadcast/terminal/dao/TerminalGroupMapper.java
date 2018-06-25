package com.zltel.broadcast.terminal.dao;

import java.util.List;

import com.zltel.broadcast.terminal.bean.TerminalGroup;

public interface TerminalGroupMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(TerminalGroup record);

    int insertSelective(TerminalGroup record);

    TerminalGroup selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TerminalGroup record);

    int updateByPrimaryKey(TerminalGroup record);
    
    public List<TerminalGroup> queryInfo(TerminalGroup record) throws Exception;
    
    int selectOid(TerminalGroup record);
}