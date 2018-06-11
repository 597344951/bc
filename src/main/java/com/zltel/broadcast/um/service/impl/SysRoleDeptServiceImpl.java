package com.zltel.broadcast.um.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.SysRoleDept;
import com.zltel.broadcast.um.dao.SysRoleDeptMapper;
import com.zltel.broadcast.um.service.SysRoleDeptService;

@Service
public class SysRoleDeptServiceImpl extends BaseDaoImpl<SysRoleDept> implements SysRoleDeptService {

    @Resource
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public BaseDao<SysRoleDept> getInstince() {
        return this.sysRoleDeptMapper;
    }

}
