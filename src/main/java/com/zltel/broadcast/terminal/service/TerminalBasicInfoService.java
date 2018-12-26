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

    public R queryBasicInfo(TerminalBasicInfo record, int pageNum, int pageSize);

    public R queryMapInfo();

    public R echarts(String string);

    public Map<String, Integer> countOnlineTerminal(TerminalBasicInfo record);

    /**
     * 同步终端信息
     * 
     * @return 同步终端数
     * @junit {@link com.zltel.broadcast.terminal.service.TerminalBasicInfoServiceTest#testSynchronizTerminalInfo()}
     */
    public int synchronizTerminalInfo();
}
