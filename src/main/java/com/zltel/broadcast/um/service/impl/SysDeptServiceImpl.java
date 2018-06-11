package com.zltel.broadcast.um.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.SysDept;
import com.zltel.broadcast.um.dao.SysDeptMapper;
import com.zltel.broadcast.um.service.SysDeptService;

@Service
public class SysDeptServiceImpl extends BaseDaoImpl<SysDept> implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public BaseDao<SysDept> getInstince() {
        return sysDeptMapper;
    }

}
