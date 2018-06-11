package com.zltel.broadcast.terminal.service;


import com.zltel.broadcast.terminal.bean.TerminalGatherBean;
import com.zltel.broadcast.terminal.bean.TerminalGroup;
import com.zltel.broadcast.common.json.R;

public interface TerminalGroupsService {
    int deleteByPrimaryKey(Integer oid);

    int insert(TerminalGroup record);

    int insertSelective(TerminalGroup record);

    int deleteGi(Integer oid);

    TerminalGroup selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TerminalGatherBean tgb);

    int updateByPrimaryKey(TerminalGroup record);

    public R queryBasicGroup(TerminalGroup record, int pageNum, int pageSize);

    public R queryBasicInfo(TerminalGroup record);

    public R insertTgb(TerminalGatherBean tgb);



}
