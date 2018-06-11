package com.zltel.broadcast.log.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.log.bean.Log;
import com.zltel.broadcast.log.bean.LogType;
import com.zltel.broadcast.log.dao.LogMapper;
import com.zltel.broadcast.log.dao.LogTypeMapper;
import com.zltel.broadcast.log.service.LogService;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;
    @Resource
    private LogTypeMapper logTypeMapper;


    @Override
    public List<LogType> queryLogType(LogType lt) {
        return logTypeMapper.query(lt, BaseDaoImpl.DEFAULT);
    }


    @Override
    public List<Log> queryLog(Log log, PageRowBounds prb) {
        return logMapper.query(log, prb);
    }



}
