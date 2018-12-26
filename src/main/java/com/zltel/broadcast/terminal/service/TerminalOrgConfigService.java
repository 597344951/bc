package com.zltel.broadcast.terminal.service;

import java.util.List;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;

public interface TerminalOrgConfigService {
    /** 查询未配置归属信息的终端 **/
    List<TerminalBasicInfo> queryUnConfigTerminal(TerminalBasicInfo info, Pager pager);

    /** 终端关联 **/
    int orgConfig(TerminalBasicInfo info);

    List<Integer> orgConfigs(List<TerminalBasicInfo> infos);

    /** 检测指定终端是否能导入 **/
    List<R> checkTerminals(List<TerminalBasicInfo> infos);

}
