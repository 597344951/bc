package com.zltel.broadcast.um.service;

import com.zltel.broadcast.um.bean.SysLog;

public interface SysLogService {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}