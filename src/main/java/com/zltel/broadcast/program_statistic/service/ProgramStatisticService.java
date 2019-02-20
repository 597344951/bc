package com.zltel.broadcast.program_statistic.service;

import java.util.List;

import com.zltel.broadcast.program_statistic.bean.ProgramStatistic;

public interface ProgramStatisticService {
    /**
     * 保存节目播放统计数据
     * @param pss
     */
    void saveStatistic(List<ProgramStatistic> pss);
    /**
     * 保存播放节目列表
     * @param list
     */
    void saveProgramPlayList(List<ProgramStatistic> list);

}
