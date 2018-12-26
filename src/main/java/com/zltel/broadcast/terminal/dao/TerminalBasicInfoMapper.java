package com.zltel.broadcast.terminal.dao;

import java.util.List;

import com.zltel.broadcast.terminal.bean.OnlineCountBean;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.bean.TerminalEcharts;

public interface TerminalBasicInfoMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(TerminalBasicInfo record);

    int insertSelective(TerminalBasicInfo record);

    TerminalBasicInfo selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(TerminalBasicInfo record);

    int updateByPrimaryKey(TerminalBasicInfo record);

    List<TerminalBasicInfo> queryBasicInfo(TerminalBasicInfo record);

    List<TerminalBasicInfo> queryAll();

    List<TerminalEcharts> ratioEcharts();

    List<TerminalEcharts> onlineEcharts();

    List<TerminalEcharts> locEcharts();

    List<TerminalEcharts> warrantyEcharts();

    List<TerminalEcharts> revEcharts();

    List<TerminalEcharts> verEcharts();

    List<TerminalEcharts> typEcharts();

    List<TerminalEcharts> tCountEcharts();

    List<OnlineCountBean> countOnlineTerminal(TerminalBasicInfo record);
    
    List<TerminalBasicInfo> queryUnConfigTerminal(TerminalBasicInfo record);

    int orgConfig(TerminalBasicInfo record);
}
