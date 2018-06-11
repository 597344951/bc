package com.zltel.broadcast.terminal.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;

public interface TerminalBasicInfoService {
    int deleteByPrimaryKey(Integer oid);

    int insert(TerminalBasicInfo record);

    int insertSelective(TerminalBasicInfo record);

    TerminalBasicInfo selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TerminalBasicInfo record);

    int updateByPrimaryKey(TerminalBasicInfo record);

    public R queryBasicInfo(TerminalBasicInfo record, int pageNum, int pageSize) throws Exception;
    
    public R echarts(String string) throws Exception;
}
