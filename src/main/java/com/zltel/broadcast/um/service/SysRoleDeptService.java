package com.zltel.broadcast.um.service;

import com.zltel.broadcast.um.bean.SysRoleDept;

public interface SysRoleDeptService {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleDept record);

    int insertSelective(SysRoleDept record);

    SysRoleDept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleDept record);

    int updateByPrimaryKey(SysRoleDept record);
}