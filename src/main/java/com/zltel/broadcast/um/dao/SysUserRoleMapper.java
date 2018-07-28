package com.zltel.broadcast.um.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.SysUserRole;

public interface SysUserRoleMapper extends BaseDao<SysUserRole> {
	/**
     * 根据条件查询用户角色
     * @param sysUserRole 条件
     * @return
     */
    public List<HashMap<String, Object>> querySysUserRoles(Map<String, Object> conditions);
    
    /**
     * 根据用户id删除用户角色
     * @param userId 条件
     * @return
     */
    public int deleteSysUserRolesByUserId(Long userId);
    
    /**
     * 删除用户角色
     * @param sysUserRole 条件
     * @return
     */
    public int deleteSysUserRolesByUpdateSysUserRole(SysUserRole sysUserRole);
}
