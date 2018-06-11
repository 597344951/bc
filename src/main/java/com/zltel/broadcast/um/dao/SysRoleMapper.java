package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.SysRole;

public interface SysRoleMapper extends BaseDao<SysRole> {
	/**
     * 根据条件查询角色
     * @param sysRole
     * @return
     */
    public List<SysRole> querySysRoles(SysRole sysRole);
}
