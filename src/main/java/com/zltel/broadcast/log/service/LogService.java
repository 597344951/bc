package com.zltel.broadcast.log.service;

import java.util.List;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.log.bean.Log;
import com.zltel.broadcast.log.bean.LogType;

public interface LogService {
    /** 查询指定日志类型 **/
    public List<LogType> queryLogType(LogType lt);

    /** 查询指定日志信息 **/
    public List<Log> queryLog(Log log, PageRowBounds prb);

}
