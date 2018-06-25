package com.zltel.broadcast.terminal.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;

public interface TerminalBasicInfoService {
    int deleteByPrimaryKey(Integer oid);

    int insert(TerminalBasicInfo record);

    int insertSelective(TerminalBasicInfo record);

    TerminalBasicInfo selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TerminalBasicInfo record);

    int updateByPrimaryKey(TerminalBasicInfo record);

    public R queryBasicInfo(TerminalBasicInfo record, int pageNum, int pageSize) ;
    
    public R echarts(String string) throws Exception;
    /**统计设备在线状态数**/
    public Map<String, Integer> countOnlineTerminal();
}
