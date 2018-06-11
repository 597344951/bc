package com.zltel.broadcast.um.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.SysLog;
import com.zltel.broadcast.um.dao.SysLogMapper;
import com.zltel.broadcast.um.service.SysLogService;

@Service
public class SysLogServiceImpl extends BaseDaoImpl<SysLog>  implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public BaseDao<SysLog> getInstince() {
       return this.sysLogMapper;
    }

     

}
